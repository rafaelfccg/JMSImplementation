package testApplication.consumer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import session.MySession;
import topic.MyTopic;
import utils.Utils;

public class MainConsumer {
	public static void main(String[] args) {
		ConnectionFactory cfactory = null;
		Connection connection = null;
		Session session = null;
		Context ctx = null;
		
		try {
			ctx = new InitialContext(Utils.enviroment());
			
			cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			connection = cfactory.createConnection();		
			session= connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("abc");
			connection.start();
			MessageConsumer consumer  = session.createConsumer(topic);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message arg0) {
					System.out.println("Message received");
					if(arg0 instanceof BytesMessage){
						try {
							BytesMessage msg = (BytesMessage)arg0;
							System.out.println(msg.readUTF());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
					
				}
			});
			
			while(true){
				//faz nada só escuta passivamente
			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				session.close();
				connection.close();
				ctx.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
