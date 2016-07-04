package testApplication.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
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
import topic.MyTopic;
import utils.Utils;

public class MainProducer {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	static Context ctx = null;
	static Connection connection = null;
	static Session session = null;
	static ConnectionFactory cfactory = null;
	static MessageProducer producer;
	
	public static void main(String[] args) throws InterruptedException {
		int rematch = 1;
		try {
			ctx = new InitialContext(Utils.enviroment());
			
			cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			connection = cfactory.createConnection();				
			session = connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("TickTackToe");
			producer  = session.createProducer(topic);
			connection.start();

			MyConnectionAdmin admin = new MyConnectionAdmin(connection);
			
			session.createTopic("abc");
			session.createTopic("abc/dcd");
			session.createTopic("abc/mjs");
			session.createTopic("bhs");
			Thread.sleep(1000);
			ArrayList<Topic> arr = admin.getTopicList();
			HashMap<String, Integer> map = admin.getSubscribersCount();
			for(int i = 0 ; i < arr.size(); i++){
				System.out.println("> " + arr.get(i).getTopicName() + " (" + map.get(arr.get(i).getTopicName()) + ")");
			}
			
			HashMap<String, HashMap<String, Double>> stats = admin.getTopicsStats();
			
			for(String t: stats.keySet()){
				System.out.println("Topic: " + t);
				System.out.println("   - Avg messages per minute: " + stats.get(t).get("average_messages_per_minute"));
				System.out.println("   - Total messages: " + stats.get(t).get("total_messages"));
			}

			sendText("The Biggest TickTackToe event of the world will begin!");
			while(rematch ==1){
				TickTackToe game = new TickTackToe();
				sendBoard(game);
				while (!game.getHasWinner()) {
					sendText("Waiting nextMove from player "+(game.getTurn()+1));
					game.nextMove();
					game.printGame();
					sendBoard(game);
				}
				sendText("The winner was player: "+ game.getWinner());
				System.out.println("Play another Match?(1 for YES , 0 to NO)");
				rematch = SCANNER.nextInt();
				if(rematch == 1){
					sendText("Next match will be in few...");
				}else{
					sendText("This is all for today, thank you for watching!");
				}
			}
	
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
			try {
				session.close();
				connection.close();
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void sendText(String msg) throws JMSException{
		System.out.println(msg);
		BytesMessage bmsg = session.createBytesMessage();
		bmsg.writeUTF(msg);
		producer.send(bmsg);
	}
	
	public static void sendBoard(TickTackToe msg) throws JMSException{
		ObjectMessage bmsg = session.createObjectMessage();
		bmsg.setObject(msg.clone());
		producer.send(bmsg);
	}
}
