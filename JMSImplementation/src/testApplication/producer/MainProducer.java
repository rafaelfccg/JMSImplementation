package testApplication.producer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import session.MySession;
import test.TickTackToe;
import topic.MyTopic;
import utils.Utils;

public class MainProducer {
	
	static Context ctx = null;
	static Connection connection = null;
	static Session session = null;
	static ConnectionFactory cfactory = null;
	static MessageProducer producer;
	
	public static void main(String[] args) {

		try {
			ctx = new InitialContext(Utils.enviroment());
			cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			connection = cfactory.createConnection();				
			session = connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			Destination topic = new MyTopic("TickTackToe");
			producer  = session.createProducer(topic);
			connection.start();
			
			sendText("The Biggest TickTackToe event of the world will begin!");
			
			TickTackToe game = new TickTackToe();
			//sendBoard(game);
			while (!game.getHasWinner()) {
				sendText("Waiting nextMove from player "+(game.getTurn()+1));
				game.nextMove();
				game.printGame();
				sendBoard(game);
			}
			sendText("The winner was player: "+ game.getWinner());
			
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
	
	
	public static void sendText(String msg) throws JMSException{
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
