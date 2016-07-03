package messages;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class MyObjectMessage extends MyMessage implements ObjectMessage{
	
	Serializable object;
	
	public MyObjectMessage(){}

	@Override
	public void clearBody() throws JMSException {
		this.object = null;
	};
	
	@Override
	public Serializable getObject() throws JMSException {
		return object;
	}

	@Override
	public void setObject(Serializable arg0) throws JMSException {
		isSettable();
		this.object = arg0;
		
	}
	
}
