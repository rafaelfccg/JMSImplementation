package connection;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import session.SessionMessageReceiverListener;

public interface MyConnectionSendMessage {
	public void sendMessage(Message my) throws IOException, JMSException;
	public void subscribe(Destination destination, SessionMessageReceiverListener session) throws IOException, JMSException;
	public void unsubscribe(Destination destination, SessionMessageReceiverListener session) throws IOException, JMSException;
}
