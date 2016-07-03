package messages;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

import session.MySessionMessageSend;
import utils.Utils;

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
		this.timeToLive = Long.MAX_VALUE;
		this.priority = 4;
	}
	
	@Override
	public void close() throws JMSException {
		this.destination = null;
		this.sessionSend = null;
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
			arg0.setJMSDeliveryMode(this.deliveryMode);
			this.sessionSend.send(arg0);
		} catch(NullPointerException exp){
			throw new JMSException("This producer is closed");
		}catch (Exception e) {
			Utils.raise(e);
		}
	}

	@Override
	public void send(Destination arg0, Message arg1) throws JMSException {
		try {
			arg1.setJMSDestination(arg0);
			arg1.setJMSPriority(this.priority);
			arg1.setJMSExpiration(this.timeToLive);
			arg1.setJMSDeliveryMode(this.deliveryMode);
			this.sessionSend.send(arg1);
		} catch(NullPointerException exp){
			throw new JMSException("This producer is closed");
		} catch (Exception e) {
			Utils.raise(e);
		}
	}

	@Override
	public void send(Message arg0, int deliveryMode, int priority, long timeToLive) throws JMSException {
		try {
			arg0.setJMSDestination(this.destination);
			arg0.setJMSPriority(priority);
			arg0.setJMSExpiration(timeToLive);
			arg0.setJMSDeliveryMode(this.deliveryMode);
			this.sessionSend.send(arg0);
		} catch(NullPointerException exp){
			throw new JMSException("This producer is closed");
		} catch (Exception e) {
			Utils.raise(e);
		}
	}

	@Override
	public void send(Destination arg0, Message arg1, int deliveryMode, int priority, long timeToLive) throws JMSException {
		try {
			arg1.setJMSDestination(arg0);
			arg1.setJMSPriority(priority);
			arg1.setJMSExpiration(timeToLive);
			arg1.setJMSDeliveryMode(deliveryMode);
			this.sessionSend.send(arg1);
		} catch(NullPointerException exp){
			throw new JMSException("This producer is closed");
		} catch (IOException e) {
			Utils.raise(e);
		}
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
