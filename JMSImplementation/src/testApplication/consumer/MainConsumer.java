package testApplication.consumer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import connection.MyConnection;
import session.MySession;
import topic.MyTopic;

public class MainConsumer {
	public static void main(String[] args) {
		try {
			Connection connection = new MyConnection("localhost",12345);		
			Session session= connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("abc");
			connection.start();
			MessageConsumer consumer  = session.createConsumer(topic);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message arg0) {
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
		}
	}
}
