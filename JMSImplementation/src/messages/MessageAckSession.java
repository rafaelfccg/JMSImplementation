package messages;

import javax.jms.JMSException;
import javax.jms.Message;

public interface MessageAckSession {
	public void ack(Message message) throws JMSException;
}
