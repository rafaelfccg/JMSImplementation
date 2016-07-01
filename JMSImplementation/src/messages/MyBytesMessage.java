package messages;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class MyBytesMessage extends MyMessage implements BytesMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1530438025104904723L;

	private byte[] messageBody;

	private DataOutputStream out = null;
	private ByteArrayOutputStream byteOut = null;

	private DataInputStream in = null;
	private ByteArrayInputStream byteIn = null;

	private int offSet;

	private DataInputStream getInputStream() throws IOException {
		if(this.in == null){
			this.byteIn = new ByteArrayInputStream(this.messageBody, this.offSet, this.messageBody.length - this.offSet);
			this.in = new DataInputStream(byteIn);
			in.mark(this.messageBody.length - this.in.available());
		}
		return in;
	}
	private DataOutputStream getOutputStream() throws IOException {
		if(this.out == null){
			this.byteOut = new ByteArrayOutputStream();
			this.out = new DataOutputStream(byteOut);
			if(this.messageBody != null) this.out.write(this.messageBody);
		}
		return out;
	}
	private final void raise(IOException exception) throws JMSException {
		JMSException error = new JMSException(exception.getMessage());
		error.setLinkedException(exception);
		throw error;
	}
	//Puts the message body in read-only mode and repositions the stream of bytes to the beginning.
	@Override
	public void reset() throws JMSException {
		try {
			if(!this.readOnly){
				this.readOnly = true;
				if(this.out != null){
					this.out.flush();
					this.messageBody = this.byteOut.toByteArray();
					this.byteOut.close();
					this.out.close();
					this.out = null;
					this.byteOut = null;
				}
			}else{
				if(this.in != null){
					this.byteIn = null;
					this.in.close();;
					this.in =null;
				}
			}
		} catch (IOException e) {
			raise(e);
		}	

	}

	@Override
	public void clearBody() throws JMSException {
		try {
			if(this.in != null){
				this.byteIn.close();
				this.in.close();
				this.in  = null;
				this.byteIn  = null;
			}
			if(this.out != null){
				this.byteOut.close();
				this.byteOut = null;
				this.out.close();
				out = null;
			}
			this.offSet = 0;
			this.messageBody = new byte[0];
			this.readOnly = false;
		} catch (IOException e) {
			raise(e);
		}	
	}

	@Override
	public long getBodyLength() throws JMSException {
		return messageBody.length;
	}

	@Override
	public boolean readBoolean() throws JMSException {
		boolean b = false;
		try {
			getInputStream();
			b =  in.readBoolean();
		} catch (IOException e) {
			raise(e);
		}
		return b;
	}

	@Override
	public byte readByte() throws JMSException {
		byte b = 0;
		try {
			getInputStream();
			b = in.readByte();
		} catch (IOException e) {
			raise(e);
		}
		return b;
	}

	@Override
	public int readBytes(byte[] arg0) throws JMSException {
		return readBytes(arg0, arg0.length);
	}

	@Override
	public int readBytes(byte[] arg0, int length) throws JMSException {
		int read = -1;

		if(length > 0 && length<= arg0.length){
			try {
				getInputStream();
				int available = this.in.available();
				if(available<=0) return -1;
				if(available<length){
					in.read(arg0, 0, available);
					read = available;
				}else{
					in.read(arg0,0,length);
					read = length;
				}
			} catch (IOException e) {
				raise(e);
			}
		}

		return read;
	}

	@Override
	public char readChar() throws JMSException {
		char c = 0;
		try {
			getInputStream();
			c = in.readChar();
		} catch (IOException e) {
			raise(e);
		}
		return c;
	}

	@Override
	public double readDouble() throws JMSException {
		double d = 0;
		try {
			getInputStream();
			d = in.readDouble();
		} catch (IOException e) {
			raise(e);
		}
		return d;
	}

	@Override
	public float readFloat() throws JMSException {
		float f = 0;
		try {
			getInputStream();
			f = in.readFloat();
		} catch (IOException e) {
			raise(e);
		}
		return f;
	}

	@Override
	public int readInt() throws JMSException {
		int d = 0;
		try {
			getInputStream();
			d = in.readInt();
		} catch (IOException e) {
			raise(e);
		}
		return d;
	}

	@Override
	public long readLong() throws JMSException {
		long d = 0;
		try {
			getInputStream();
			d = in.readLong();
		} catch (IOException e) {
			raise(e);
		}
		return d;
	}

	@Override
	public short readShort() throws JMSException {
		short d = 0;
		try {
			getInputStream();
			d = in.readShort();
		} catch (IOException e) {
			raise(e);
		}
		return d;
	}

	@Override
	public String readUTF() throws JMSException {
		String utf = null;
		try {
			getInputStream();
			utf = in.readUTF();
		} catch (IOException e) {
			raise(e);
		}
		return utf;
	}

	@Override
	public int readUnsignedByte() throws JMSException {
		int ub = 0;
		try {
			getInputStream();
			ub = in.readUnsignedByte();
		} catch (IOException e) {
			raise(e);
		}
		return ub;
	}

	@Override
	public int readUnsignedShort() throws JMSException {
		int ub = 0;
		try {
			getInputStream();
			ub = in.readUnsignedShort();
		} catch (IOException e) {
			raise(e);
		}
		return ub;
	}

	@Override
	public void writeBoolean(boolean arg0) throws JMSException {
		try {
			getOutputStream().writeBoolean(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeByte(byte arg0) throws JMSException {
		try {
			getOutputStream().writeByte(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeBytes(byte[] arg0) throws JMSException {
		try {
			getOutputStream().write(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeBytes(byte[] arg0, int offset, int length) throws JMSException {
		try {
			getOutputStream().write(arg0,offset,length);
		} catch (IOException e) {
			raise(e);
		}
	}

	@Override
	public void writeChar(char arg0) throws JMSException {
		try {
			getOutputStream().writeChar(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeDouble(double arg0) throws JMSException {
		try {
			getOutputStream().writeDouble(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeFloat(float arg0) throws JMSException {
		try {
			getOutputStream().writeFloat(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeInt(int arg0) throws JMSException {
		try {
			getOutputStream().writeInt(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeLong(long arg0) throws JMSException {
		try {
			getOutputStream().writeLong(arg0);
		} catch (IOException e) {
			raise(e);
		}
	}

	@Override
	public void writeObject(Object value) throws JMSException {
		if (value instanceof Boolean) {
            writeBoolean(((Boolean) value).booleanValue());
        } else if (value instanceof Byte) {
            writeByte(((Byte) value).byteValue());
        } else if (value instanceof Short) {
            writeShort(((Short) value).shortValue());
        } else if (value instanceof Character) {
            writeChar(((Character) value).charValue());
        } else if (value instanceof Integer) {
            writeInt(((Integer) value).intValue());
        } else if (value instanceof Long) {
            writeLong(((Long) value).longValue());
        } else if (value instanceof Float) {
            writeFloat(((Float) value).floatValue());
        } else if (value instanceof Double) {
            writeDouble(((Double) value).doubleValue());
        } else if (value instanceof String) {
            writeUTF((String) value);
        } else if (value instanceof byte[]) {
            writeBytes((byte[]) value);
        } else if (value == null) {
            throw new NullPointerException(
                "BytesMessage does not support null");
        } else if (value instanceof Serializable){       
        	/*try {
        		ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(value);
				out.write(bos.toByteArray());
				oos.close();
				bos.close();
			} catch (IOException e) {
				raise(e);
			}
        	
        } else {*/
        	   throw new JMSException("Cannot write objects of type=" +
            value.getClass().getName());
        }

	}

	@Override
	public void writeShort(short arg0) throws JMSException {
		try {
			getOutputStream().writeShort(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}

	@Override
	public void writeUTF(String arg0) throws JMSException {
		try {
			getOutputStream().writeUTF(arg0);
		} catch (IOException e) {
			raise(e);
		}

	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if (this.out != null) {
            this.out.flush();
            this.messageBody = this.byteOut.toByteArray();
        }

        super.writeExternal(out);
        out.writeLong(serialVersionUID);
        out.writeInt(this.messageBody.length);
        out.write(this.messageBody);
        out.flush();
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
        long version = in.readLong();
        if (version == serialVersionUID) {
            int length = in.readInt();
            this.messageBody = new byte[length];
            in.readFully(this.messageBody);
        } else {
            throw new IOException("Incorrect version enountered: " + version +
                ". This version = " + serialVersionUID);
        }
		
	}

}
