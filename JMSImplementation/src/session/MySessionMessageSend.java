package session;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;

public interface MySessionMessageSend {
	public void send(Message message) throws IOException, JMSException;
}
