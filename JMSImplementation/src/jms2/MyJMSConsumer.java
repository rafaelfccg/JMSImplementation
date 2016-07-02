package jms2;

import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

public class MyJMSConsumer implements JMSConsumer{

	MessageConsumer msgConsumer;
	
	public MyJMSConsumer(MessageConsumer msgc){
		this.msgConsumer = msgc;
	}
	
	@Override
	public void close() {
		try {
			this.msgConsumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public MessageListener getMessageListener() throws JMSRuntimeException {
		try {
			return this.msgConsumer.getMessageListener();
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public String getMessageSelector() {
		try {
			return this.msgConsumer.getMessageSelector();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message receive() {
		try {
			return this.msgConsumer.receive();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message receive(long arg0) {
		try {
			return this.msgConsumer.receive(arg0);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T receiveBody(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T receiveBody(Class<T> arg0, long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T receiveBodyNoWait(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receiveNoWait() {
		try {
			return this.msgConsumer.receiveNoWait();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setMessageListener(MessageListener arg0) throws JMSRuntimeException {
		try {
			this.msgConsumer.setMessageListener(arg0);
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

}
