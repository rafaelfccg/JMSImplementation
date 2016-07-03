package messages;

import java.util.Enumeration;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class MyMapMessage extends MyMessage implements MapMessage{

	HashMap<String,Object> mapBody;
	
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
		return 0;
	}
	@Override
	public int getInt(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getLong(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Enumeration getMapNames() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getObject(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public short getShort(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getString(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean itemExists(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setBoolean(String arg0, boolean arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setByte(String arg0, byte arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBytes(String arg0, byte[] arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBytes(String arg0, byte[] arg1, int arg2, int arg3) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setChar(String arg0, char arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDouble(String arg0, double arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFloat(String arg0, float arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setInt(String arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLong(String arg0, long arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setObject(String arg0, Object arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setShort(String arg0, short arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setString(String arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}
	
	
}
