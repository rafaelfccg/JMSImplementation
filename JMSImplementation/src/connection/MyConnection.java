package connection;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

import utils.ClientRequestHandler;
import utils.Marshaller;


public class MyConnection implements Connection, MyConnectionSendMessage {

	private String clientId;
	private ExceptionListener exceptionListener;
	private ConnectionMetaData connectionMetaData;
	private ClientRequestHandler connection;
	private boolean open = true;
	private boolean modified = false;
	
	private void isOpen() throws JMSException{
		if(!this.open){
			throw new JMSException("Operation perfomed on closed session");
		}
	}
	
	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
		setModified();
		isOpen();
		
		this.open = false;
		
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Destination arg0, String arg1, ServerSessionPool arg2, int arg3)
			throws JMSException {
		// TODO Auto-generated method stub
		setModified();
		isOpen();
		return null;
	}

	@Override
	public ConnectionConsumer createDurableConnectionConsumer(Topic arg0, String arg1, String arg2,
			ServerSessionPool arg3, int arg4) throws JMSException {
		// TODO Auto-generated method stub
		setModified();
		isOpen();
		
		return null;
	}

	@Override
	public Session createSession(boolean arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		setModified();
		isOpen();
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
		if(modified){
			throw new JMSException("Set the client ID must be performed before any other action.");
		}
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

	public void setModified() {
		this.modified = true;
	}

	@Override
	public void send(Message myMessage) throws IOException {
		connection.send(Marshaller.marshall(myMessage));
	}
}
