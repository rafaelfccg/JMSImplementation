package benchmark;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import connection.MyConnectionAdmin;
import messages.MyObjectMessage;
import session.MySession;
import topic.MyTopic;
import utils.Utils;

public class ConsumerThread implements Runnable{
	
	@Override
	public void run() {
		
		try{
			Context ctx = new InitialContext(Utils.enviroment());
			ConnectionFactory cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			Connection connection = cfactory.createConnection();
			Session session = connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			connection.start();
			MessageConsumer consumer  = session.createConsumer(new MyTopic("a"));
			
			long sum = 0;
			
			consumer.receive();
			long lastReceived = System.currentTimeMillis(); 
			
			for(int i=0; i < Benchmark.MESSAGES - 1; i++){
				MyObjectMessage m = (MyObjectMessage) consumer.receive();
				try{
					//System.out.println(m.getObject().toString());
				}catch(Exception e){
					e.printStackTrace();
				}
				long now = System.currentTimeMillis();
				sum += now - lastReceived;
			}
			
			System.out.println("[" + connection.getClientID() + "] Average time: " + (sum/Benchmark.MESSAGES));
			session.close();
			System.out.println("Consumer close session");
			connection.close();
			System.out.println("Consumer close connection");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
