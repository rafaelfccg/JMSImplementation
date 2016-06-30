package connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

import server.query.Query;
import server.query.SubscriberQuery;
import session.MySession;
import session.SessionMessageReceiverListener;
import topic.MyTopic;
import utils.ClientRequestHandler;

public class MyConnection implements Connection, MyConnectionSendMessage {

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
	private HashMap<Destination,ArrayList<SessionMessageReceiverListener>> subscribed;
	private ArrayList<Session> sessions;
	
	public  MyConnection(String hostIp, int hostPort){
		this.hostPort = hostPort;
		this.hostIp = hostIp;
		this.subscribed = new HashMap<Destination,ArrayList<SessionMessageReceiverListener>>();
		this.sessions = new ArrayList<Session>();
	}
	private void isOpen() throws JMSException{
		if(!this.open){
			throw new JMSException("Operation perfomed on closed session");
		}
	}
	public void onMessageReceived(Message message){
		Destination destination;
		if(!this.stopped){
			try {
				destination = message.getJMSDestination();
				ArrayList<SessionMessageReceiverListener> sessions = this.subscribed.get(destination);
				for(SessionMessageReceiverListener session : sessions ){
					session.onMessageReceived(message);
				}
			} catch (JMSException e) {
				e.printStackTrace();
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
		this.open = false;
		try {
			this.receiverConnection.closeConnection();
			this.senderConnection.closeConnection();
		} catch (IOException e) {
			callExceptionListener(e);
			e.printStackTrace();
		}
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
						System.out.println("Connected receiver");
						senderConnection = new ClientRequestHandler(hostIp, hostPort, false, getClientID());
						this.open = true;
						System.out.println("Connected consumer");
						this.stopped = false;
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
		senderConnection.sendMessageAsync(myMessage);
	}
	
	private void subscribeSessionToDestination(Destination destination, SessionMessageReceiverListener session){
		ArrayList<SessionMessageReceiverListener> arr = this.subscribed.get(destination);
		if(arr == null){
			arr = new ArrayList<SessionMessageReceiverListener>();
			this.subscribed.put(destination, arr);
		}else{
			if(!arr.contains(session)){
				arr.add(session);
			}
		}
	}
	
	private boolean unsubscribeSessionToDestination(Destination destination, SessionMessageReceiverListener session) throws JMSException{
		ArrayList<SessionMessageReceiverListener> arr = this.subscribed.get(destination);
		if(arr == null){
			throw new JMSException("Try to unsubscribe from a topic you have not subscribed before");
		}else{
			arr.remove(session);
			if(arr.isEmpty()) return true;
			else return false;
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
		//UnsubscriberQuery
		if(unsubcribe){
		//AbstractQuery query = new SubscriberQuery(getClientID(),topic.getTopicName());
		//publisherConnection.send(query);
		}
		
	}
	@Override
	public void acknowledgeMessage(Message message, Session session) throws IOException, JMSException {
		isOpen();
		setModified();
		//AckQuery
		//AbstractQuery query = new SubscriberQuery(getClientID(),topic.getTopicName());
		//publisherConnection.send(query);
	}
	@Override
	public void closeSession(Session session) {
		// TODO Auto-generated method stub
		
	}
}
