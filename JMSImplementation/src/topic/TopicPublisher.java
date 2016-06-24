package topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

public class TopicPublisher implements MessageProducer{

	private int priority;
	private int deliveryMode;
	private boolean disableMessageId;
	private boolean disableMessageTimestamp;
	private long timeToLive;
	
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
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
