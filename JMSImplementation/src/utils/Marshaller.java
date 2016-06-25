package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.jms.Message;

public class Marshaller {

	public static byte[] marshall(Message message) throws IOException{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
		objectStream.writeObject(message);
		return byteStream.toByteArray();	
	}
	
	public static Message unmarshall(byte[] bytes) throws IOException, ClassNotFoundException{	
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectStream = new ObjectInputStream(byteStream);
		return (Message) objectStream.readObject();
	}
	
}
