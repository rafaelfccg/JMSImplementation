package Connection;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

public class MyConnection implements Connection {

	private String clientId;
	private ExceptionListener exceptionListener;
	private ConnectionMetaData connectionMetaData;
	
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Destination arg0, String arg1, ServerSessionPool arg2, int arg3)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionConsumer createDurableConnectionConsumer(Topic arg0, String arg1, String arg2,
			ServerSessionPool arg3, int arg4) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session createSession(boolean arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientID() throws JMSException {
		return clientId;
	}

	@Override
	public ExceptionListener getExceptionListener() throws JMSException {
		return exceptionListener;
	}

	@Override
	public ConnectionMetaData getMetaData() throws JMSException {
		return connectionMetaData;
	}

	@Override
	public void setClientID(String arg0) throws JMSException {
		clientId = arg0;	
	}

	@Override
	public void setExceptionListener(ExceptionListener arg0) throws JMSException {
		exceptionListener = arg0;
		
	}

	@Override
	public void start() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws JMSException {
		// TODO Auto-generated method stub
		
	}
}