package Messages;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;

public class MyBytesMessage extends MyMessage implements BytesMessage{

	Destination destination;
	String jmsMessageId;
	
	@Override
	public long getBodyLength() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean readBoolean() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte readByte() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readBytes(byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readBytes(byte[] arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char readChar() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDouble() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float readFloat() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readInt() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long readLong() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short readShort() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readUTF() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int readUnsignedByte() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readUnsignedShort() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBoolean(boolean arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByte(byte arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBytes(byte[] arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBytes(byte[] arg0, int arg1, int arg2) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeChar(char arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDouble(double arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFloat(float arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeInt(int arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLong(long arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeObject(Object arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeShort(short arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeUTF(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

}
