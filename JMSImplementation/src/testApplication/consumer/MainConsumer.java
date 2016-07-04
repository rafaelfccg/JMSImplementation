package testApplication.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import connection.MyConnectionAdmin;
import session.MySession;
import test.TickTackToe;
import utils.Utils;

public class MainConsumer {
	private static Scanner in;
	private static ArrayList<Topic> arr;
	
	public static int selectChannelFromContext(MyConnectionAdmin admin) throws JMSException, NamingException{
		int selected = 0;
		arr = admin.getTopicList();
		HashMap<String, Integer> map = admin.getSubscribersCount();

		do{
			System.out.println("Select one of the opened channels:");
			System.out.println("0 - Quit Application");
			for(int i = 0 ; i< arr.size(); i++){
				System.out.println((i+1)+" - "+arr.get(i).getTopicName()+" (" + map.get(arr.get(i).getTopicName()) + ")");
			}
			System.out.println(arr.size()+1+" - Refresh Channels");
			
			
			boolean invalid  = false;
			do{
				selected = in.nextInt();
				invalid = selected < 0 && selected >(arr.size()+1);
				if(invalid){
					System.out.println("Please select a valid channel");
				}
			}while(invalid);
			if(selected == 0) return -1;
			if(selected == arr.size()+1){
				arr = admin.getTopicList();
				map = admin.getSubscribersCount();
			}
		}while(selected == arr.size()+1);
		return selected-1;
	}
	
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
			connection.start();
			int exit = -1;
			in = new Scanner(System.in);
			
			MyConnectionAdmin admin = new MyConnectionAdmin(connection);
			
			while(true){
				while(exit != 1){
					System.out.println("If you want to start a streaming please type 1");
					System.out.println("If you want to quit the application please type 0");
					exit = in.nextInt();
					if(exit == 0) return;
				}
				int selected = selectChannelFromContext(admin);;
				if(selected == -1) return ;
				
				System.out.println("You may quit the streaming any time by typing 0");
				System.out.println("Connection The game");
				
				MessageConsumer consumer  = session.createConsumer(arr.get(selected));
				
				consumer.setMessageListener(new MessageListener() {
					@Override
					public void onMessage(Message arg0) {
						try {
							Topic topic = (Topic)arg0.getJMSDestination();
							System.out.println("On topic "+topic.getTopicName());
							if(arg0 instanceof BytesMessage){							
								BytesMessage msg = (BytesMessage)arg0;
								System.out.println(msg.readUTF());
							}else if(arg0 instanceof ObjectMessage){
								TickTackToe game;
								Object o = ((ObjectMessage) arg0).getObject();
								if(o instanceof TickTackToe){
									game = (TickTackToe)o;
									game.printGame();
								}
							}
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				});
				
				MessageProducer producer  = session.createProducer(arr.get(selected));
				BytesMessage bmsg = session.createBytesMessage();
				bmsg.writeUTF("Consumer Entrou na sala");
				producer.send(bmsg);
				
				while(exit != 0){
					exit = in.nextInt();
					if(exit!= 0){
						System.out.println("You may quit the streaming any time by typing 0");
					}
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
				System.exit(0);
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
