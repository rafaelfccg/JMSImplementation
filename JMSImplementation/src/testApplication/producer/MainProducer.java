package testApplication.producer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import session.MySession;
import topic.MyTopic;
import utils.Utils;

public class MainProducer {
	public static void main(String[] args) {
		Context ctx = null;
		Connection connection = null;
		Session session = null;
		ConnectionFactory cfactory = null;
		try {
			ctx = new InitialContext(Utils.enviroment());
			cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			connection = cfactory.createConnection();				
			session= connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("abc");
			MessageProducer producer  = session.createProducer(topic);
			BytesMessage bmsg = session.createBytesMessage();
			connection.start();
			bmsg.writeUTF("This is a test!");
			producer.send(bmsg);
			
			while(true){
				Thread.sleep(1000000);
			}
		} catch (JMSException e) {
			System.out.println("Entrou no JMSExcep");
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				System.out.println("Entrou no finally");
				session.close();
				connection.close();
				ctx.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
