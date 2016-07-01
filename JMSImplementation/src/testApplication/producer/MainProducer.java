package testApplication.producer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import connection.MyConnection;
import session.MySession;
import topic.MyTopic;

public class MainProducer {
	public static void main(String[] args) {
		try {
			Connection connection = new MyConnection("localhost",12345);		
			Session session= connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("abc");
			MessageProducer producer  = session.createProducer(topic);
			BytesMessage bmsg = session.createBytesMessage();
			connection.start();
			bmsg.writeUTF("This is a test!");
			producer.send(bmsg);
			
			while(true){
				//espera forever
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}
}
