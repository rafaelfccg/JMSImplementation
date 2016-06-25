package messages;

import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

public class MyObjectMessage extends MyMessage implements ObjectMessage{

	@Override
	public Serializable getObject() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObject(Serializable arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	
}
