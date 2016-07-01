package session;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import connection.MyConnectionSendMessage;
import messages.MessageAckSession;
import messages.MyBytesMessage;
import messages.MyMessageConsumer;
import messages.MyMessageProducer;
import utils.Utils;

public class MySession implements Session, SessionMessageReceiverListener, SessionConsumerOperations, MessageAckSession,MySessionMessageSend{

	public final static int AUTO_ACKNOWLEDGE = 1;
	public final static int CLIENT_ACKNOWLEDGE  = 2;
	MyConnectionSendMessage connection;
	boolean transacted;
	int acknowledgeMode;
	MessageListener messageListener;
	HashMap<Destination, ArrayList<MessageListener>> subscribedList;
	boolean closed;
	
	public MySession(boolean trans, int ack, MyConnectionSendMessage connection){
		this.transacted = trans;
		this.acknowledgeMode = ack;
		this.connection = connection;
		this.subscribedList = new HashMap<Destination, ArrayList<MessageListener>>();
		closed = false;
	}
	
	public void isOpen() throws JMSException{
		if(closed){
			throw new JMSException("Consumer closed");
		}
	}
	@Override
	public void close() throws JMSException {
		synchronized (this) {
			if(!closed){
				closed = true;
				connection.closeSession(this);
				for(Destination d : subscribedList.keySet()){
					ArrayList<MessageListener> arr = this.subscribedList.get(d);
					for(MessageListener msg : arr){
						if(msg instanceof MessageConsumer){
							MessageConsumer msgC = (MessageConsumer) msg;
							msgC.close();
						}
					}
				}
			}
		}
	}

	@Override
	public void commit() throws JMSException {
		throw new IllegalStateException("this JMS implementation does not implement transacted Sessions");
	}

	@Override
	public QueueBrowser createBrowser(Queue arg0) throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public QueueBrowser createBrowser(Queue arg0, String arg1) throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public BytesMessage createBytesMessage() throws JMSException {
		BytesMessage bmsg = new MyBytesMessage();
		return bmsg;
	}

	@Override
	public MessageConsumer createConsumer(Destination destination) throws JMSException {
		return createConsumer(destination, null, false);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String selector) throws JMSException {
		return createConsumer(destination, selector, false);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String selector, boolean noLocal) throws JMSException {
		isOpen();
		try {
			MyMessageConsumer msgConsumer = new MyMessageConsumer(destination, selector,noLocal,this);
			this.connection.subscribe(destination, this);
			ArrayList<MessageListener> list = this.subscribedList.get(destination);
			if( list == null){
				list = new ArrayList<MessageListener>();
				this.subscribedList.put(destination, list);
			}
			list.add(msgConsumer);
			return msgConsumer;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic arg0, String arg1, String arg2, boolean arg3)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapMessage createMapMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message createMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectMessage createObjectMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageProducer createProducer(Destination arg0) throws JMSException {
		isOpen();
		MessageProducer msgProducer = new MyMessageProducer(arg0,this);
		return msgProducer;
	}

	@Override
	public Queue createQueue(String arg0) throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public StreamMessage createStreamMessage() throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public TemporaryQueue createTemporaryQueue() throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public TemporaryTopic createTemporaryTopic() throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public TextMessage createTextMessage() throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public TextMessage createTextMessage(String arg0) throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public Topic createTopic(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAcknowledgeMode() throws JMSException {
		return this.acknowledgeMode;
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return this.messageListener;
	}

	@Override
	public boolean getTransacted() throws JMSException {
		throw new IllegalStateException("this JMS implementation does not implement transacted Sessions");
	}

	@Override
	public void recover() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() throws JMSException {
		throw new IllegalStateException("this JMS implementation does not implement transacted Sessions");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMessageListener(MessageListener arg0) throws JMSException {
		this.messageListener = arg0;
	}

	@Override
	public void unsubscribe(String arg0) throws JMSException {
		try {
			this.connection.unsubscribe(arg0, this);
		} catch (IOException e) {
			Utils.raise(e);
		}
	}

	@Override
	public void onMessageReceived(Message message) {
		Destination destination;
		try {
			destination = message.getJMSDestination();
			ArrayList<MessageListener> consumers = this.subscribedList.get(destination);
			for(MessageListener consumer : consumers ){
				consumer.onMessage(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ack(Message message) throws JMSException {
		try {
			this.connection.acknowledgeMessage(message,this);
		} catch (IOException e) {
			Utils.raise(e);
		}
	}

	@Override
	public void send(Message message) throws IOException, JMSException {
		connection.sendMessage(message);
	}

	@Override
	public void closeConsumer(MyMessageConsumer consumer) {
		ArrayList<MessageListener> arr = this.subscribedList.get(consumer.getDestination());
		arr.remove(consumer);
		
	}

}
