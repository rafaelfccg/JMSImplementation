package connection;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import session.SessionMessageReceiverListener;

public interface MyConnectionSendMessage {
	public void sendMessage(Message my) throws IOException, JMSException;
	public void createTopic(Topic my) throws IOException, JMSException;
	public void subscribe(Destination destination, SessionMessageReceiverListener session) throws IOException, JMSException;
	public void unsubscribe(String destination, SessionMessageReceiverListener session) throws IOException, JMSException;
	public void acknowledgeMessage(Message message, Session session) throws IOException, JMSException;
	public void closeSession(Session session);
}
