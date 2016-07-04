package testApplication.consumer;

import java.util.Scanner;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import session.MySession;
import test.TickTackToe;
import topic.MyTopic;
import utils.Utils;

public class MainConsumer {
	private static Scanner in;

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
			Destination topic = new MyTopic("TickTackToe");
			
			int exit = -1;
			in = new Scanner(System.in);
			while(exit != 1){
				System.out.println("If you want to start the streaming please type 1");
				System.out.println("If you want to quit the application please type 0");
				exit = in.nextInt();
				if(exit == 0) return;
			}
			System.out.println("You may quit the streaming any time by typing 0");
			Thread.sleep(1000);
			System.out.println("Connection The game");
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
					}else if(arg0 instanceof ObjectMessage){
						TickTackToe game;
						try {
							game = (TickTackToe) ((ObjectMessage) arg0).getObject();
							game.printGame();
						} catch (JMSException e) {
							e.printStackTrace();
						}
						
					}
					
				}
			});
			
			while(exit != 0){
				exit = in.nextInt();
				if(exit!= 0){
					System.out.println("You may quit the streaming any time by typing 0");
				}
			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
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
