package myJmsAPI;

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;

import topic.MyTopic;
import utils.Utils;

public class MyJmsAPI {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Topic> requestTopicList(Context ctx) throws NamingException, JMSException{
		Connection connection = getConnectionFromContext(ctx);
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = new MyTopic(Utils.LIST_TOPIC);
		connection.start();
		MessageConsumer consumer = session.createConsumer(topic);
		ArrayList<Topic> list = null;
		Message message  = consumer.receive();
		if(message instanceof ObjectMessage){
			Object o = ((ObjectMessage) message).getObject();
			if (o instanceof ArrayList)list = (ArrayList<Topic>)o; 
		}
		session.close();
		connection.close();
		return list;
	}
	
	public static Connection getConnectionFromContext(Context ctx) throws NamingException, JMSException{
		ConnectionFactory cfactory = null;
		Connection connection = null;
		cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
		connection = cfactory.createConnection();				
		return connection;
	}

}
