package connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class MyConnectionFactory implements ConnectionFactory{
	
	protected String providerIp;
	protected int providerPort;
	
	public MyConnectionFactory(String ip, int port) {
		this.providerIp = ip;
		this.providerPort = port;
	}

	@Override
	public Connection createConnection() throws JMSException {
		return (Connection) new MyConnection(this.providerIp, this.providerPort);
	}

	@Override
	public Connection createConnection(String arg0, String arg1) throws JMSException {
		// TODO  Auth
		throw new JMSException("Authentication not Implemented yet");
	}

}
