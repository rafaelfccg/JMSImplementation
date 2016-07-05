package benchmark;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import session.MySession;
import topic.MyTopic;
import utils.Utils;

public class ProducerThread implements Runnable {
	
	@Override
	public void run() {
		
		try{
			Context ctx = new InitialContext(Utils.enviroment());
			ConnectionFactory cfactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			Connection connection = cfactory.createConnection();
			Session session = connection.createSession(false, MySession.AUTO_ACKNOWLEDGE);
			connection.start();
			MessageProducer producer  = session.createProducer(new MyTopic("a"));
			
			for(int i=0; i < Benchmark.MESSAGES; i++){
				Message msg = session.createObjectMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce pellentesque est vel convallis tempor. Fusce volutpat libero ornare libero aliquam sagittis. Pellentesque vel diam risus.");
				producer.send(msg);
			}
			
			//System.out.println("[Producer] Average time: " + (sum/Benchmark.MESSAGES));
			
			session.close();
			connection.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
