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

public class MySession implements Session, SessionMessageReceiverListener, SessionConsumerOperations, MessageAckSession,MySessionMessageSend{

	public final static int AUTO_ACKNOWLEDGE = 1;
	public final static int CLIENT_ACKNOWLEDGE  = 2;
	MyConnectionSendMessage connection;
	boolean transacted;
	int acknowledgeMode;
	MessageListener messageListener;
	HashMap<Destination, ArrayList<MessageListener>> subscribedList;
	
	public MySession(boolean trans, int ack, MyConnectionSendMessage connection){
		this.transacted = trans;
		this.acknowledgeMode = ack;
		this.connection = connection;
		this.subscribedList = new HashMap<Destination, ArrayList<MessageListener>>();
	}
	
	@Override
	public void close() throws JMSException {
		connection.closeSession(this);
		
	}

	@Override
	public void commit() throws JMSException {
		// TODO Auto-generated method stub
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
		try {
			MyMessageConsumer msgConsumer = new MyMessageConsumer(destination, this);
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
	public MessageConsumer createConsumer(Destination arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer createConsumer(Destination arg0, String arg1, boolean arg2) throws JMSException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextMessage createTextMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextMessage createTextMessage(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Topic createTopic(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAcknowledgeMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return this.messageListener;
	}

	@Override
	public boolean getTransacted() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recover() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() throws JMSException {
		// TODO Auto-generated method stub
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
