package connection;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;

public class MyConnectionConsumer implements ConnectionConsumer {

	ServerSessionPool serverSessionPool;
	
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServerSessionPool getServerSessionPool() throws JMSException {
		return serverSessionPool;
	}

}
