package messages;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class MyObjectMessage extends MyMessage implements ObjectMessage{
	
	Serializable object;
	
	@Override
	public Serializable getObject() throws JMSException {
		return object;
	}

	@Override
	public void setObject(Serializable arg0) throws JMSException {
		this.object = arg0;
		
	}
	
}
