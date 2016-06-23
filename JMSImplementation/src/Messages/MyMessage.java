package Messages;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Collections;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MyMessage implements Message {
	
	private Destination destination;
	private Destination replyTo;
	private String jmsMessageId;
	private String jmsCorrelationId;
	private String jmsType;
	private Integer jmsDeliveryMode;
	private Long timestamp;
	private Long timeToLive;
	
	HashMap<String,Object> properties;
	
	private Integer priority;
	private Boolean redelivered;
	
	MessageAckSession sessionAck;
	
	static private final String BOOLEAN_PROPERTY = "BOOLEAN";
	static private final String OBJECT_PROPERTY = "OBJECT";
	static private final String STRING_PROPERTY = "STRING";
	static private final String INTEGER_PROPERTY = "INTEGER";
	static private final String FLOAT_PROPERTY = "FLOAT";
	static private final String LONG_PROPERTY = "LONG";
	static private final String DOUBLE_PROPERTY = "DOUBLE";
	static private final String SHORT_PROPERTY = "SHORT";
	static private final String BYTE_PROPERTY = "BYTE";
	
	@Override
	public void acknowledge() throws JMSException {
		if(sessionAck!=null){
			sessionAck.ack(this);
		}
		throw new JMSException("Cannot acknowledge message, unknown session");
	}

	@Override
	public void clearBody() throws JMSException {
		throw new JMSException("This method must be overwriten by its super classes");
	}

	@Override
	public void clearProperties() throws JMSException {
		this.properties.clear();
	}

	@Override
	public boolean getBooleanProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.BOOLEAN_PROPERTY);
		if(b instanceof Boolean){
			return (Boolean) b;
		}
		throw new JMSException("No boolean Propety");
	}

	@Override
	public byte getByteProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.BYTE_PROPERTY);
		if(b instanceof Byte){
			return (Byte) b;
		}
		throw new JMSException("No byte Propety");
	}

	@Override
	public double getDoubleProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.DOUBLE_PROPERTY);
		if(b instanceof Double){
			return (Double) b;
		}
		throw new JMSException("No double Propety");
	}

	@Override
	public float getFloatProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.FLOAT_PROPERTY);
		if(b instanceof Float){
			return (Float) b;
		}
		throw new JMSException("No float Propety");
	}

	@Override
	public int getIntProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.INTEGER_PROPERTY);
		if(b instanceof Integer){
			return (Integer) b;
		}
		throw new JMSException("No integer Propety");
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		return this.jmsCorrelationId;
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		if(this.jmsCorrelationId == null) throw new JMSException("correlationId is null");
		return this.jmsCorrelationId.getBytes();
	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		return this.jmsDeliveryMode;
	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		return this.destination;
	}

	@Override
	public long getJMSExpiration() throws JMSException {
		return this.timeToLive;
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		return this.jmsMessageId;
	}

	@Override
	public int getJMSPriority() throws JMSException {
		return this.priority;
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		return this.redelivered;
	}

	@Override
	public Destination getJMSReplyTo() throws JMSException {
		return this.replyTo;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return this.timestamp;
	}

	@Override
	public String getJMSType() throws JMSException {
		return this.jmsType;
	}

	@Override
	public long getLongProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.LONG_PROPERTY);
		if(b instanceof Long){
			return (Long) b;
		}
		throw new JMSException("No long Propety");
	}

	@Override
	public Object getObjectProperty(String arg0) throws JMSException {		
		Object b = this.properties.get(MyMessage.OBJECT_PROPERTY);
		if(b != null){
			return (Byte) b;
		}
		throw new JMSException("No Object Propety");
	}

	@Override
	public Enumeration<String> getPropertyNames() throws JMSException {
		return Collections.enumeration(this.properties.keySet());
	}

	@Override
	public short getShortProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.SHORT_PROPERTY);
		if(b instanceof Short){
			return (Short) b;
		}
		throw new JMSException("No short Propety");
	}

	@Override
	public String getStringProperty(String arg0) throws JMSException {
		Object b = this.properties.get(MyMessage.STRING_PROPERTY);
		if(b instanceof String){
			return (String) b;
		}
		throw new JMSException("No String Propety");
	}

	@Override
	public boolean propertyExists(String arg0) throws JMSException {
		return this.properties.containsKey(arg0);
	}

	@Override
	public void setBooleanProperty(String arg0, boolean arg1) throws JMSException {
		this.properties.put(BOOLEAN_PROPERTY, new Boolean(arg1));
	}

	@Override
	public void setByteProperty(String arg0, byte arg1) throws JMSException {
		this.properties.put(BYTE_PROPERTY, new Byte(arg1));
		
	}

	@Override
	public void setDoubleProperty(String arg0, double arg1) throws JMSException {
		this.properties.put(DOUBLE_PROPERTY, new Double(arg1));
	}

	@Override
	public void setFloatProperty(String arg0, float arg1) throws JMSException {
		this.properties.put(FLOAT_PROPERTY, new Float(arg1));
	}

	@Override
	public void setIntProperty(String arg0, int arg1) throws JMSException {
		this.properties.put(INTEGER_PROPERTY, new Integer(arg1));
	}

	@Override
	public void setJMSCorrelationID(String arg0) throws JMSException {
		this.jmsCorrelationId = arg0;
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
		this.jmsCorrelationId = new String(arg0);
	}

	@Override
	public void setJMSDeliveryMode(int arg0) throws JMSException {
		this.jmsDeliveryMode = new Integer(arg0);
	}

	@Override
	public void setJMSDestination(Destination arg0) throws JMSException {
		this.destination = arg0;
	}

	@Override
	public void setJMSExpiration(long arg0) throws JMSException {
		this.timeToLive = new Long(arg0);
	}

	@Override
	public void setJMSMessageID(String arg0) throws JMSException {
		this.jmsMessageId = arg0;
	}

	@Override
	public void setJMSPriority(int arg0) throws JMSException {
		this.priority = new Integer(arg0);
	}

	@Override
	public void setJMSRedelivered(boolean arg0) throws JMSException {
		this.redelivered = new Boolean(arg0);
	}

	@Override
	public void setJMSReplyTo(Destination arg0) throws JMSException {
		this.replyTo = arg0;
	}

	@Override
	public void setJMSTimestamp(long arg0) throws JMSException {
		this.timestamp = new Long(arg0);
	}

	@Override
	public void setJMSType(String arg0) throws JMSException {
		this.jmsType = arg0;
	}

	@Override
	public void setLongProperty(String arg0, long arg1) throws JMSException {
		this.properties.put(LONG_PROPERTY, new Long(arg1));
	}

	@Override
	public void setObjectProperty(String arg0, Object arg1) throws JMSException {
		this.properties.put(OBJECT_PROPERTY, arg1);
	}

	@Override
	public void setShortProperty(String arg0, short arg1) throws JMSException {
		this.properties.put(SHORT_PROPERTY, new Short(arg1));
	}

	@Override
	public void setStringProperty(String arg0, String arg1) throws JMSException {
		this.properties.put(STRING_PROPERTY, arg1);
	}

}
