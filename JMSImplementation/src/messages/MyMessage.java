package messages;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Collections;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public class MyMessage implements Message, Serializable, Externalizable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 958482877712819617L;
	
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
	
	private static int messageId = 0;
	
	protected boolean readOnly = false;
	
	public void setReadOnly(boolean b){
		this.readOnly = b;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(destination);
		out.writeObject(replyTo);
		out.writeObject(this.jmsMessageId);
		out.writeObject(this.jmsCorrelationId);
		out.writeObject(this.jmsType);
		out.writeObject(this.jmsDeliveryMode);
		out.writeObject(this.timestamp);
		out.writeObject(this.timeToLive);
		out.flush();
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		destination = (Destination)in.readObject();
		this.replyTo = (Destination)in.readObject();
		this.jmsMessageId = (String) in.readObject();
		this.jmsCorrelationId = (String) in.readObject();
		this.jmsType = (String)in.readObject();
		this.jmsDeliveryMode = (Integer) in.readObject();
		this.timestamp = (Long)in.readObject();
		this.timeToLive =  (Long)in.readObject();
	}
	
	public MyMessage(){
		this.jmsMessageId = "MSG:"+UUID.randomUUID().toString()+ messageId;
		this.timestamp = System.currentTimeMillis();
		messageId++;
	}
	
	public boolean isAlive(){
		return (System.currentTimeMillis() - this.timestamp) <= this.timeToLive;
	}
	
	@Override
	public void acknowledge() throws JMSException {
		if(sessionAck!=null){
			sessionAck.ack(this);
		}
		throw new JMSException("Cannot acknowledge message, unknown session");
	}

	@Override
	public void clearBody() throws JMSException {
		//no body to clean
	}

	@Override
	public void clearProperties() throws JMSException {
		this.properties.clear();
	}

	@Override
	public boolean getBooleanProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof Boolean){
			return (Boolean) b;
		}
		throw new JMSException("No boolean Propety");
	}

	@Override
	public byte getByteProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof Byte){
			return (Byte) b;
		}
		throw new JMSException("No byte Propety");
	}

	@Override
	public double getDoubleProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof Double){
			return (Double) b;
		}
		throw new JMSException("No double Propety");
	}

	@Override
	public float getFloatProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof Float){
			return (Float) b;
		}
		throw new JMSException("No float Propety");
	}

	@Override
	public int getIntProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
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
		Object b = this.properties.get(arg0);
		if(b instanceof Long){
			return (Long) b;
		}
		throw new JMSException("No long Propety");
	}

	@Override
	public Object getObjectProperty(String arg0) throws JMSException {		
		Object b = this.properties.get(arg0);
		if(b != null){
			return b;
		}
		throw new JMSException("No Object Propety");
	}

	@Override
	public Enumeration<String> getPropertyNames() throws JMSException {
		return Collections.enumeration(this.properties.keySet());
	}

	@Override
	public short getShortProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof Short){
			return (Short) b;
		}
		throw new JMSException("No short Propety");
	}

	@Override
	public String getStringProperty(String arg0) throws JMSException {
		Object b = this.properties.get(arg0);
		if(b instanceof String){
			return (String) b;
		}
		throw new JMSException("No String Propety");
	}

	@Override
	public boolean propertyExists(String arg0) throws JMSException {
		return this.properties.containsKey(arg0);
	}
	protected void isSettable() throws JMSException{
		if(this.readOnly){
			throw new JMSException("This message is Read-Only");
		}
	}
	@Override
	public void setBooleanProperty(String arg0, boolean arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Boolean(arg1));
	}

	@Override
	public void setByteProperty(String arg0, byte arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Byte(arg1));
		
	}

	@Override
	public void setDoubleProperty(String arg0, double arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Double(arg1));
	}

	@Override
	public void setFloatProperty(String arg0, float arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Float(arg1));
	}

	@Override
	public void setIntProperty(String arg0, int arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Integer(arg1));
	}

	@Override
	public void setJMSCorrelationID(String arg0) throws JMSException {
		isSettable();
		this.jmsCorrelationId = arg0;
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
		isSettable();
		this.jmsCorrelationId = new String(arg0);
	}

	@Override
	public void setJMSDeliveryMode(int arg0) throws JMSException {
		isSettable();
		this.jmsDeliveryMode = new Integer(arg0);
	}

	@Override
	public void setJMSDestination(Destination arg0) throws JMSException {
		isSettable();
		this.destination = arg0;
	}

	@Override
	public void setJMSExpiration(long arg0) throws JMSException {
		isSettable();
		this.timeToLive = new Long(arg0);
	}

	@Override
	public void setJMSMessageID(String arg0) throws JMSException {
		isSettable();
		this.jmsMessageId = arg0;
	}

	@Override
	public void setJMSPriority(int arg0) throws JMSException {
		isSettable();
		this.priority = new Integer(arg0);
	}

	@Override
	public void setJMSRedelivered(boolean arg0) throws JMSException {
		this.redelivered = new Boolean(arg0);
	}

	@Override
	public void setJMSReplyTo(Destination arg0) throws JMSException {
		isSettable();
		this.replyTo = arg0;
	}

	@Override
	public void setJMSTimestamp(long arg0) throws JMSException {
		isSettable();
		this.timestamp = new Long(arg0);
	}

	@Override
	public void setJMSType(String arg0) throws JMSException {
		isSettable();
		this.jmsType = arg0;
	}

	@Override
	public void setLongProperty(String arg0, long arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Long(arg1));
	}

	@Override
	public void setObjectProperty(String arg0, Object arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, arg1);
	}

	@Override
	public void setShortProperty(String arg0, short arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, new Short(arg1));
	}

	@Override
	public void setStringProperty(String arg0, String arg1) throws JMSException {
		isSettable();
		this.properties.put(arg0, arg1);
	}

}
