package connection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import messages.MyMapMessage;
import topic.MyTopic;
import utils.Utils;

public class MyConnectionAdmin{
	
	private Connection connection;
	
	public static final String LIST_TOPICS = "admin_list_topics";
	
	public static final String TOPICS_CONSUMERS = "admin_topics_consumers";
	
	public MyConnectionAdmin(Connection connection) {
		super();
		this.connection = connection;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Topic> getTopicList() throws NamingException, JMSException{
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = new MyTopic(MyConnectionAdmin.LIST_TOPICS);
		connection.start();
		MessageConsumer consumer = session.createConsumer(topic);
		ArrayList<Topic> list = null;
		Message message  = consumer.receive();
		if(message instanceof ObjectMessage){
			Object o = ((ObjectMessage) message).getObject();
			if (o instanceof ArrayList)list = (ArrayList<Topic>)o; 
		}
		session.close();
		return list;
	}
	
	public HashMap<String, Integer> getSubscribersCount() throws NamingException, JMSException{
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = new MyTopic(MyConnectionAdmin.TOPICS_CONSUMERS);
		connection.start();
		MessageConsumer consumer = session.createConsumer(topic);
		ArrayList<Topic> list = null;
		MyMapMessage message  = (MyMapMessage)consumer.receive();
		session.close();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (Enumeration<String> e = message.getMapNames(); e.hasMoreElements();){
			String t = e.nextElement();
			map.put(t, message.getInt(t));
		}
		return map;
	}

}
