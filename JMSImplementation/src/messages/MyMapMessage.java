package messages;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class MyMapMessage extends MyMessage implements MapMessage{

	HashMap<String,Object> mapBody;
	
	public MyMapMessage(){
		this.mapBody = new HashMap<String,Object>();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeObject(mapBody);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
		this.mapBody =  (HashMap<String,Object>) in.readObject();
	}
	
	@Override
	public boolean getBoolean(String arg0) throws JMSException {
		return (Boolean) this.mapBody.get(arg0);
	}
	@Override
	public byte getByte(String arg0) throws JMSException {
		return (Byte) this.mapBody.get(arg0);
	}
	@Override
	public byte[] getBytes(String arg0) throws JMSException {
		return (byte []) this.mapBody.get(arg0);
	}
	@Override
	public char getChar(String arg0) throws JMSException {
		return (Character) this.mapBody.get(arg0);
	}
	@Override
	public double getDouble(String arg0) throws JMSException {
		return (Double) this.mapBody.get(arg0);
	}
	@Override
	public float getFloat(String arg0) throws JMSException {
		return (Float) this.mapBody.get(arg0);
	}
	@Override
	public int getInt(String arg0) throws JMSException {
		return (Integer) this.mapBody.get(arg0);
	}
	@Override
	public long getLong(String arg0) throws JMSException {
		return (Long) this.mapBody.get(arg0);
	}
	@Override
	public Enumeration getMapNames() throws JMSException {
		return new Vector(this.mapBody.keySet()).elements();
	}
	@Override
	public Object getObject(String arg0) throws JMSException {
		return (Object) this.mapBody.get(arg0);
	}
	@Override
	public short getShort(String arg0) throws JMSException {
		return (Short) this.mapBody.get(arg0);
	}
	@Override
	public String getString(String arg0) throws JMSException {
		return (String) this.mapBody.get(arg0);
	}
	@Override
	public boolean itemExists(String arg0) throws JMSException {
		return this.mapBody.containsKey(arg0);
	}
	@Override
	public void setBoolean(String arg0, boolean arg1) throws JMSException {
		this.mapBody.put(arg0, (Boolean) arg1);
	}
	@Override
	public void setByte(String arg0, byte arg1) throws JMSException {
		this.mapBody.put(arg0, (byte) arg1);
	}
	@Override
	public void setBytes(String arg0, byte[] arg1) throws JMSException {
		this.mapBody.put(arg0, (byte[]) arg1);
	}
	@Override
	public void setBytes(String arg0, byte[] arg1, int arg2, int arg3) throws JMSException {
		this.mapBody.put(arg0, (byte[]) arg1);
	}
	@Override
	public void setChar(String arg0, char arg1) throws JMSException {
		this.mapBody.put(arg0, (Character) arg1);
	}
	@Override
	public void setDouble(String arg0, double arg1) throws JMSException {
		this.mapBody.put(arg0, (Double) arg1);
	}
	@Override
	public void setFloat(String arg0, float arg1) throws JMSException {
		this.mapBody.put(arg0, (Float) arg1);
	}
	@Override
	public void setInt(String arg0, int arg1) throws JMSException {
		this.mapBody.put(arg0, (Integer) arg1);
	}
	@Override
	public void setLong(String arg0, long arg1) throws JMSException {
		this.mapBody.put(arg0, (Long) arg1);
	}
	@Override
	public void setObject(String arg0, Object arg1) throws JMSException {
		this.mapBody.put(arg0, (Object) arg1);
	}
	@Override
	public void setShort(String arg0, short arg1) throws JMSException {
		this.mapBody.put(arg0, (Short) arg1);
	}
	@Override
	public void setString(String arg0, String arg1) throws JMSException {
		this.mapBody.put(arg0, (String) arg1);
	}
	
	
}
