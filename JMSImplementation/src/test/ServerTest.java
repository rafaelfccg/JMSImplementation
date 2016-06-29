package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.query.Query;
import server.query.QueryType;
import server.query.SubscriberQuery;

public class ServerTest {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket producerSocket = new Socket("localhost", 12345);
		ObjectOutputStream producerOutputStream = new ObjectOutputStream(producerSocket.getOutputStream());
		ObjectInputStream producerInputStream = new ObjectInputStream(producerSocket.getInputStream());
		Query registerProducerQuery = new Query("123", QueryType.REGISTER_SENDER);
		producerOutputStream.writeObject(registerProducerQuery);
		Query registerProducerAck = (Query) producerInputStream.readObject();
		System.out.println("Producer Socket registered " + registerProducerAck.getClientId());
		
		Socket consumerSocket = new Socket("localhost", 12345);
		ObjectOutputStream consumerOutputStream = new ObjectOutputStream(consumerSocket.getOutputStream());
		ObjectInputStream consumerInputStream = new ObjectInputStream(consumerSocket.getInputStream());
		Query registerConsumerQuery = new Query("123", QueryType.REGISTER_RECEIVER);
		consumerOutputStream.writeObject(registerConsumerQuery);
		Query registerConsumerAck = (Query) consumerInputStream.readObject();
		System.out.println("Consumer Socket registered " + registerConsumerAck.getClientId());
		
		
		while(true) {}
		
	}
	
}
