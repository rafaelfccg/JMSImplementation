package messages;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

public class MyMessageConsumer implements MessageConsumer, MessageListener {

	MessageListener messageListener;
	//MessageSelector messageSelector;
	
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return messageListener;
	}

	@Override
	public String getMessageSelector() throws JMSException {
		//return messageSelector
		return null;
	}

	@Override
	public Message receive() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receive(long arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receiveNoWait() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMessageListener(MessageListener arg0) throws JMSException {
		messageListener = arg0;
		
	}

	@Override
	public void onMessage(Message message) {
        try {
            if (messageListener != null) {
            	messageListener.onMessage(message);
            } else {
                System.err.println("MessageListener no longer registered");
            }
        } catch (Throwable exception) {
        	exception.printStackTrace();
        }
	}
}
