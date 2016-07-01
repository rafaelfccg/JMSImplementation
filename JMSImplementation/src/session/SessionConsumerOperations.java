package session;

import javax.jms.JMSException;

import messages.MyMessageConsumer;

public interface SessionConsumerOperations {
	public void closeConsumer(MyMessageConsumer consumer) throws JMSException;
}
