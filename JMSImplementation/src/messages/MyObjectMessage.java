package messages;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class MyObjectMessage extends MyMessage implements ObjectMessage{
	
	Serializable object;
	
	public MyObjectMessage(){}

	
	public MyObjectMessage(Serializable arg0) {
		this.object = arg0;
	}


	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeObject(object);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
		this.object =  (Serializable) in.readObject();
	}
	
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
