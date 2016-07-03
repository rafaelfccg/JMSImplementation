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
import messages.MyMessage;
import messages.MyMessageConsumer;
import messages.MyMessageProducer;
import messages.MyObjectMessage;
import topic.MyTopic;
import topic.MyTopicSubscriber;
import utils.Utils;

public class MySession implements Session, SessionMessageReceiverListener, SessionConsumerOperations, MessageAckSession,MySessionMessageSend{

	MyConnectionSendMessage connection;
	boolean transacted;
	int acknowledgeMode;
	MessageListener messageListener;
	HashMap<String, ArrayList<MessageListener>> subscribedList;
	boolean closed;
	
	public MySession(boolean trans, int ack, MyConnectionSendMessage connection){
		this.transacted = trans;
		this.acknowledgeMode = ack;
		this.connection = connection;
		this.subscribedList = new HashMap<String, ArrayList<MessageListener>>();
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
				for(String d : subscribedList.keySet()){
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
			Topic topic  = (Topic) destination;
			ArrayList<MessageListener> list = this.subscribedList.get(topic.getTopicName());
			if( list == null){
				list = new ArrayList<MessageListener>();
			}
			list.add(msgConsumer);
			this.subscribedList.put(topic.getTopicName(), list);
			return msgConsumer;
		} catch (IOException e) {
			Utils.raise(e);
		}
		return null;
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic arg0, String arg1) throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal)
			throws JMSException {
		MyTopicSubscriber tsubs = new MyTopicSubscriber(topic, name, messageSelector, noLocal, this);
		
		return tsubs;
	}

	@Override
	public MapMessage createMapMessage() throws JMSException {
		throw new JMSException("Not Implemented method");
	}

	@Override
	public Message createMessage() throws JMSException {
		return new MyMessage();
	}

	@Override
	public ObjectMessage createObjectMessage() throws JMSException {
		return new MyObjectMessage();
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable arg0) throws JMSException {
		throw new JMSException("Not Implemented method");
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
		Topic topic = new MyTopic(arg0);
		try {
			connection.createTopic(topic);
		} catch (IOException e) {
			Utils.raise(e);
		}
		return topic;
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
		Topic destination;
		MyMessage msg = (MyMessage)message;
		msg.setSessionAck(this);
		try {
			destination = (Topic)msg.getJMSDestination();
			ArrayList<MessageListener> consumers = this.subscribedList.get(destination.getTopicName());
			
			for(MessageListener consumer : consumers ){
				consumer.onMessage(msg);
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
		System.out.println("Entrou no session");
		connection.sendMessage(message);
	}

	@Override
	public void closeConsumer(MyMessageConsumer consumer) throws JMSException {
		Topic topic = (Topic)consumer.getDestination();
		ArrayList<MessageListener> arr = this.subscribedList.get(topic.getTopicName());
		arr.remove(consumer);
	}

	@Override
	public void received(Message message) throws JMSException {
		if(this.acknowledgeMode == Session.AUTO_ACKNOWLEDGE){
			message.acknowledge();
		}
	}

}
