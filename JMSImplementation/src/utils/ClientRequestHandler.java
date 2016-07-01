package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.jms.Message;

import connection.MyConnection;
import server.query.MessageQuery;
import server.query.Query;
import server.query.QueryType;

public class ClientRequestHandler {

	private String hostname;
	private int port;
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private MyConnection connection;
	
	
	public ClientRequestHandler(String hostname, int port,boolean isSubscriber, String clientId) throws UnknownHostException, ClassNotFoundException , IOException{
		this.hostname = hostname;
		this.port = port;
		this.socket = new Socket(this.hostname, this.port);
		this.output = new ObjectOutputStream(this.socket.getOutputStream());
		this.input = new ObjectInputStream(this.socket.getInputStream());
		sendType(isSubscriber,clientId);
		waitAck(isSubscriber);	
	}
	
	private void sendType(boolean isSubscriber, String clientId) throws IOException {
		Query type;
		if(isSubscriber){
			type= new Query(clientId, QueryType.REGISTER_RECEIVER);
		}else{
			type= new Query(clientId, QueryType.REGISTER_SENDER);
		}
		send(type);
		
	}

	private void waitAck(boolean isSubscriber) throws ClassNotFoundException, IOException{
		Query ack = (Query) this.input.readObject();
		if(isSubscriber && ack.getType() == QueryType.REGISTER_RECEIVER_ACK  ||
				!isSubscriber && ack.getType() == QueryType.REGISTER_SENDER_ACK){
			return;
		}
		throw new IOException("Ack was not received");
	}
	
	public Object sendAndReceive(Object object) throws IOException, ClassNotFoundException{
		send(object);
		return receive();
	}
	 
	public void send(Object object) throws IOException{
		this.output.writeObject(object);
	}
	public void sendMessageAsync(Query query){
		MyMessageSender sender = new MyMessageSender();
		sender.message = query;
		Thread senderThread = new Thread(sender);
		senderThread.start();
	}
	public Object receive() throws IOException, ClassNotFoundException{
		Object object  = this.input.readObject();
		return object;
	}
	
	public void closeConnection() throws IOException{
		this.socket.close();
	}
	public void startMessageReceving(){
		Thread receiver = new Thread(new MyMessageReceiver());
		receiver.start();
	}
	
	private class MyMessageReceiver implements Runnable{
		@Override
		public void run() {
			while(!socket.isClosed()){
				try {
					Query message = (Query)receive();
					connection.onMessageReceived(message);
				} catch (ClassNotFoundException e) {
					System.err.println("Wrong message Type Received");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class MyMessageSender implements Runnable{
		Query message;
		@Override
		public void run() {
			if(!socket.isClosed()){
				try {
					send(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
