package connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

import messages.MyMessage;
import server.query.AckQuery;
import server.query.CreateTopicQuery;
import server.query.MessageQuery;
import server.query.Query;
import server.query.QueryType;
import server.query.SubscriberQuery;
import server.query.TopicQuery;
import server.query.UnsubscriberQuery;
import session.MySession;
import session.SessionMessageReceiverListener;
import topic.MyTopic;
import utils.ClientRequestHandler;

public class MyConnection implements Connection, MyConnectionSendMessage, Runnable {

	private String clientId;
	private String hostIp;
	private int hostPort;
	private ExceptionListener exceptionListener;
	private ConnectionMetaData connectionMetaData;
	private ClientRequestHandler receiverConnection;
	private ClientRequestHandler senderConnection;
	private boolean open = false;
	private boolean stopped = false;
	private boolean modified = false;
	private HashMap<String,ArrayList<SessionMessageReceiverListener>> subscribed;
	private ConcurrentLinkedQueue<MessageWaitingAck> waitingAck;
	
	private ArrayList<Session> sessions;
	ReentrantLock lock = new ReentrantLock();
	private Condition messageSent;
	private Condition ackReceived;
	
	private static int  id = 0;
	
	public  MyConnection(String hostIp, int hostPort){
		this.lock = new ReentrantLock();
		this.hostPort = hostPort;
		this.hostIp = hostIp;
		this.subscribed = new HashMap<String,ArrayList<SessionMessageReceiverListener>>();
		this.sessions = new ArrayList<Session>();
		this.clientId = "CLT:"+UUID.randomUUID().toString()+id;
		this.messageSent = lock.newCondition();
		this.ackReceived = lock.newCondition();
		id++;
		this.waitingAck = new ConcurrentLinkedQueue<MessageWaitingAck>();
	}
	private void isOpen() throws JMSException{
		if(!this.open){
			throw new JMSException("Operation perfomed on closed session");
		}
	}
	public void onMessageReceived(Query query){
		Topic destination;
		if(!this.stopped){
			try {
				if(query instanceof MessageQuery){
					Message msg = ((MessageQuery) query).getMessage();
					destination = (Topic)msg.getJMSDestination();
					lock.lock();
					ArrayList<SessionMessageReceiverListener> sessions = this.subscribed.get(destination.getTopicName());
					String[] arr2 = new String[1];
					this.subscribed.keySet().toArray(arr2);
					if(sessions != null){
						for(SessionMessageReceiverListener session : sessions ){
							session.onMessageReceived(msg);
						}
					}
				}else if(query instanceof AckQuery){
					AckQuery ackQuery = (AckQuery) query;
					MessageWaitingAck remove = new MessageWaitingAck(ackQuery.getMessageID());
					this.lock.lock();
					this.waitingAck.remove(remove);
					this.ackReceived.signalAll();
					this.lock.unlock();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}finally{
				if(lock.isLocked())lock.unlock();
			}
		}
		
	}
	private void callExceptionListener(Exception e){
		JMSException error = new JMSException(e.getMessage());
		error.setLinkedException(e);
		if(this.exceptionListener != null){ 
			this.exceptionListener.onException(error);
		}
	}
	@Override
	public void close() throws JMSException {
		setModified();
		lock.lock();
		this.open = false;
		try {
			while(!this.waitingAck.isEmpty()){
				this.ackReceived.await();
			}
			if(this.receiverConnection != null) this.receiverConnection.closeConnection();
			if(this.senderConnection != null) this.senderConnection.closeConnection();
		} catch (IOException e) {
			callExceptionListener(e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			callExceptionListener(e);
			e.printStackTrace();
		}
		this.messageSent.signalAll();
		lock.unlock();
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Destination arg0, String arg1, ServerSessionPool arg2, int arg3)
			throws JMSException {
		throw new JMSException("Method not Implemented");
	}

	@Override
	public ConnectionConsumer createDurableConnectionConsumer(Topic arg0, String arg1, String arg2,
			ServerSessionPool arg3, int arg4) throws JMSException {
		throw new JMSException("Method not Implemented");
	}

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
		setModified();
		Session session = new MySession(transacted,acknowledgeMode,this);
		this.sessions.add(session);
		return session;
	}

	@Override
	public String getClientID() throws JMSException {
		return clientId;
	}

	@Override
	public ExceptionListener getExceptionListener() throws JMSException {
		return exceptionListener;
	}

	@Override
	public ConnectionMetaData getMetaData() throws JMSException {
		return connectionMetaData;
	}

	@Override
	public void setClientID(String arg0) throws JMSException {
		if(modified){
			throw new JMSException("Set the client ID must be performed before any other action.");
		}
		clientId = arg0;	
	}

	@Override
	public void setExceptionListener(ExceptionListener arg0) throws JMSException {
		exceptionListener = arg0;
		
	}

	@Override
	public void start() throws JMSException {
		try {
			if(!this.open){
				int numberOfTries = 0;
				while(numberOfTries<3){
					numberOfTries++;
					try {
						receiverConnection = new ClientRequestHandler(hostIp, hostPort, true, getClientID());
						senderConnection = new ClientRequestHandler(hostIp, hostPort, false, getClientID());
						this.open = true;
						this.stopped = false;
						this.receiverConnection.setConnection(this);
						this.senderConnection.setConnection(this);
						this.receiverConnection.startMessageReceving();
						Thread listenAcks = new Thread(this);
						listenAcks.start();
						break;
					} catch (Exception e) {
						e.printStackTrace();
						if(receiverConnection != null) receiverConnection.closeConnection();
						if(senderConnection != null) senderConnection.closeConnection();
					}
					int delay = (int) (1000*Math.pow(2, numberOfTries));
					System.out.println("Connection failure, trying again in "+ delay + "...");
					Thread.sleep(delay);
				}
				
				if(numberOfTries >=3) throw new JMSException("Could not connect to server");
			}
		} catch (Exception e) {
			callExceptionListener(e);
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws JMSException {
		this.stopped = true;
	}

	public void setModified() {
		this.modified = true;
	}

	@Override
	public void sendMessage(Message myMessage) throws IOException, JMSException {
		isOpen();
		setModified();
		this.waitingAck.add(new MessageWaitingAck(myMessage));
		this.lock.lock();
		if(lock.hasWaiters(this.messageSent)){
			this.messageSent.signalAll();
		}
		this.lock.unlock();
		
		MyMessage msg = (MyMessage) myMessage;
		msg.setReadOnly(true);
		Query query = new MessageQuery(getClientID(),msg);
		senderConnection.sendMessageAsync(query);
	}
	
	private void subscribeSessionToDestination(Destination destination, SessionMessageReceiverListener session) throws JMSException{
		lock.lock();
		Topic topic  = (Topic) destination;
		ArrayList<SessionMessageReceiverListener> arr = this.subscribed.get(topic.getTopicName());
		if(arr == null){
			arr = new ArrayList<SessionMessageReceiverListener>();
			arr.add(session);
		}else{
			if(!arr.contains(session)){
				arr.add(session);
			}
		}
		this.subscribed.put(topic.getTopicName(), arr);
		lock.unlock();
	}
	
	private boolean unsubscribeSessionToDestination(Destination destination, SessionMessageReceiverListener session) throws JMSException{
		
		try{
			lock.lock();
			Topic topic  = (Topic) destination;
			ArrayList<SessionMessageReceiverListener> arr = this.subscribed.get(topic.getTopicName());
			if(arr == null){
				throw new JMSException("Try to unsubscribe from a topic you have not subscribed before");
			}else{
				arr.remove(session);
				if(arr.isEmpty()) return true;
				else return false;
			}
		}finally{
			lock.unlock();
		}
	}
	@Override
	public void subscribe(Destination destination, SessionMessageReceiverListener session) throws IOException, JMSException {
		isOpen();
		setModified();
		Topic topic = (Topic) destination;
		subscribeSessionToDestination(destination, session);
		Query query = new SubscriberQuery(getClientID(),topic.getTopicName());
		senderConnection.send(query);
	}
	@Override
	public void unsubscribe(String destination, SessionMessageReceiverListener session)
			throws IOException, JMSException {
		isOpen();
		setModified();
		Topic topic = new MyTopic(destination);
		boolean unsubcribe = unsubscribeSessionToDestination(topic, session);
		if(unsubcribe){
			Query query = new UnsubscriberQuery(getClientID(),topic.getTopicName());
			senderConnection.send(query);
		}
		
	}
	@Override
	public void acknowledgeMessage(Message message, Session session) throws IOException, JMSException {
		isOpen();
		setModified();
		Query query = new AckQuery(getClientID(),message.getJMSMessageID());
		senderConnection.sendMessageAsync(query);
	}
	@Override
	public void closeSession(Session session) {
		for(String key:this.subscribed.keySet()){
			if(this.subscribed.get(key).contains(session)){
				this.subscribed.get(key).remove(session);
			}
		}
	}
	@Override
	public void createTopic(Topic my) throws IOException, JMSException {
		TopicQuery query = new TopicQuery(getClientID(), my.getTopicName());
		this.senderConnection.sendMessageAsync(query);
	}
	@Override
	public void run() {
		while(true){
			if(!this.open) return;
			if(this.waitingAck.isEmpty()){
				lock.lock();
				if(!this.open) return;
				try {
					this.messageSent.await();
				} catch (InterruptedException e) {}
				lock.unlock();
			}
			if(!this.open) return;
			MessageWaitingAck  msg = this.waitingAck.peek();
			long curr =System.currentTimeMillis();
			if(msg.getTimestamp() <= curr){
				try {
					this.waitingAck.remove(msg);
					this.sendMessage(msg.getMessage());
				} catch (IOException | JMSException e) {
					callExceptionListener(e);
					e.printStackTrace();
				}
			}else{
				try {
					Thread.sleep(msg.getTimestamp()-curr);
				} catch (InterruptedException e) {
					callExceptionListener(e);
					e.printStackTrace();
				}
			}
			if(this.lock.isHeldByCurrentThread()) this.lock.unlock();
		}	
	}
	
	private class MessageWaitingAck {
		private Message message;
		private long timestamp;
		private String messageId;
		
		public MessageWaitingAck(Message message) throws JMSException{
			this.message = message;
			this.timestamp =  System.currentTimeMillis() + 10000;
			this.messageId = message.getJMSMessageID();
			
		}
		
		public MessageWaitingAck(String messageId){
			this.messageId = messageId;
		}
		public Message getMessage(){
			return message;
		}
		public String getMessageID(){
			return messageId;
		}

		public long getTimestamp() {
			return timestamp;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof MessageWaitingAck){
				return ((MessageWaitingAck) o).getMessageID().equals(getMessageID());
			}
			return false;
		}
	}
}
