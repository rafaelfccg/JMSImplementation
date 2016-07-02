package connection;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.spi.ObjectFactory;

public class MyConnectionFactory implements ConnectionFactory, Referenceable, ObjectFactory{

	protected String providerIp;
	protected int providerPort;

	public MyConnectionFactory(){}
	
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

	@Override
	public Reference getReference() throws NamingException {
		return new Reference(MyConnectionFactory.class.getName() , MyConnectionFactory.class.getName(), null);
	}

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		if (obj instanceof Reference) {
			Reference ref = (Reference) obj;

			if (ref.getClassName().equals(MyConnectionFactory.class.getName())) {
				return new MyConnectionFactory("localhost",12345);
			}
		}
		return null;
	}

}
