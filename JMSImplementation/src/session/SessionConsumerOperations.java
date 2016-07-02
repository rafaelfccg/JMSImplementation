package session;

import javax.jms.JMSException;
import javax.jms.Message;

import messages.MyMessageConsumer;

public interface SessionConsumerOperations {
	public void closeConsumer(MyMessageConsumer consumer) throws JMSException;
	public void received(Message message) throws JMSException;
}
