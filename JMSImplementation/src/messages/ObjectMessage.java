package messages;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public class ObjectMessage extends MyMessage implements Message{
	
	private int jmsDeliveryMode;
	private long jmsExpiration;
	private String jmsMessageId;
	private int jmsPriority;
	private boolean jmsRedelivered;
	private long jmsTimestamp;
	private String jmsType;
	
	@Override
	public void acknowledge() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBody() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearProperties() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBooleanProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByteProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloatProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		return jmsDeliveryMode;
	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSExpiration() throws JMSException {
		return jmsExpiration;
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		return jmsMessageId;
	}

	@Override
	public int getJMSPriority() throws JMSException {
		return jmsPriority;
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		return jmsRedelivered;
	}

	@Override
	public Destination getJMSReplyTo() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return jmsTimestamp;
	}

	@Override
	public String getJMSType() throws JMSException {
		return jmsType;
	}

	@Override
	public long getLongProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getObjectProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getPropertyNames() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShortProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean propertyExists(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBooleanProperty(String arg0, boolean arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setByteProperty(String arg0, byte arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDoubleProperty(String arg0, double arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFloatProperty(String arg0, float arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIntProperty(String arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJMSCorrelationID(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJMSDeliveryMode(int arg0) throws JMSException {
		jmsDeliveryMode = arg0;
		
	}

	@Override
	public void setJMSDestination(Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJMSExpiration(long arg0) throws JMSException {
		jmsExpiration = arg0;
		
	}

	@Override
	public void setJMSMessageID(String arg0) throws JMSException {
		jmsMessageId = arg0;
		
	}

	@Override
	public void setJMSPriority(int arg0) throws JMSException {
		jmsPriority = arg0;
		
	}

	@Override
	public void setJMSRedelivered(boolean arg0) throws JMSException {
		jmsRedelivered = arg0;
		
	}

	@Override
	public void setJMSReplyTo(Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJMSTimestamp(long arg0) throws JMSException {
		jmsTimestamp = arg0;
		
	}

	@Override
	public void setJMSType(String arg0) throws JMSException {
		jmsType = arg0;
		
	}

	@Override
	public void setLongProperty(String arg0, long arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObjectProperty(String arg0, Object arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShortProperty(String arg0, short arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStringProperty(String arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

}
