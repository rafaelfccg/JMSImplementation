package messages;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

import session.MySessionMessageSend;

public class MyMessageProducer implements MessageProducer{

	private int deliveryMode;
	private boolean disableMessageId;
	private boolean disableMessageTimestamp;
	private int priority;
	private long timeToLive;
	private Destination destination;
	private MySessionMessageSend sessionSend;
	
	public MyMessageProducer(Destination destination, MySessionMessageSend session){
		this.destination = destination;
		this.sessionSend = session;
	}
	
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDeliveryMode() throws JMSException {
		return deliveryMode;
	}

	@Override
	public Destination getDestination() throws JMSException {
		return this.destination;
	}

	@Override
	public boolean getDisableMessageID() throws JMSException {
		return disableMessageId;
	}

	@Override
	public boolean getDisableMessageTimestamp() throws JMSException {
		return disableMessageTimestamp;
	}

	@Override
	public int getPriority() throws JMSException {
		return priority;
	}

	@Override
	public long getTimeToLive() throws JMSException {
		return timeToLive;
	}

	@Override
	public void send(Message arg0) throws JMSException {
		try {
			arg0.setJMSDestination(this.destination);
			arg0.setJMSPriority(this.priority);
			arg0.setJMSExpiration(this.timeToLive);
			this.sessionSend.send(arg0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(Destination arg0, Message arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Message arg0, int arg1, int arg2, long arg3) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Destination arg0, Message arg1, int arg2, int arg3, long arg4) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeliveryMode(int arg0) throws JMSException {
		deliveryMode = arg0;
		
	}

	@Override
	public void setDisableMessageID(boolean arg0) throws JMSException {
		disableMessageId = arg0;
	}

	@Override
	public void setDisableMessageTimestamp(boolean arg0) throws JMSException {
		disableMessageTimestamp = arg0;
		
	}

	@Override
	public void setPriority(int arg0) throws JMSException {
		priority = arg0;
		
	}

	@Override
	public void setTimeToLive(long arg0) throws JMSException {
		timeToLive = arg0;
	}

}
