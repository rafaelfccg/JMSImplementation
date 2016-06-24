package test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.query.SubscriberQuery;

public class ServerTest {

	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket socket = new Socket("localhost", 12345);
		
		ObjectOutputStream inputStream = new ObjectOutputStream(socket.getOutputStream());
		
		SubscriberQuery query = new SubscriberQuery(1, "/teste");
		
		inputStream.writeObject(query);
		
		socket.close();
		
	}
	
}
