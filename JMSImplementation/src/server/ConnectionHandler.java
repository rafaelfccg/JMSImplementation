package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.query.Query;
import server.query.QueryType;
import server.query.SubscriberQuery;

enum ConnectionHandlerType{
	UNKNOWN,
	SENDER, // client sends message through this socket, server only listens
	RECEIVER // client receives message through this socket, server only sends
}

public class ConnectionHandler implements Runnable{
	
	private static final Logger logger = Logger.getLogger( Server.class.getName() );
	
	private int id;
	
	private Server server;
	
	private Socket socket;
	
	private ObjectOutputStream outputStream;
	
	private ObjectInputStream inputStream;
	
	private boolean running;
	
	private ConnectionHandlerType type;
	
	private LinkedBlockingQueue<Object> toSend;
	
	public ConnectionHandler(int id, Socket socket, Server server) throws IOException{
		this.id = id;
		this.socket = socket;
		this.server = server;
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		this.inputStream = new ObjectInputStream(socket.getInputStream());
		this.running = true;
		this.type = ConnectionHandlerType.UNKNOWN;
		this.toSend = new LinkedBlockingQueue<Object>();
	}

	@Override
	public void run() {

		// Register this socket as a CONSUMER or PRODUCER
		try {
			this.handleRegister();
		} catch (Exception e) {
			this.running = false;
		}
		
		// Execute different actions based on the type of this socket
		while(running){
			try {
				
				if(this.type == ConnectionHandlerType.SENDER){
					handleReceivedMessages();
				}else if(this.type == ConnectionHandlerType.RECEIVER){
					sendMessages();
				}
				
			} catch (Exception e) {
				this.running = false;
			}
		}
		
		try {
			this.outputStream.close();
			this.inputStream.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/**
	 * Main method for executing a consumer action.
	 * Receives a message, handles it and then sends the ACK.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void handleReceivedMessages() throws ClassNotFoundException, IOException{
		Query query = (Query) this.inputStream.readObject();

		switch(query.getType()){
			case SUBSCRIBE:
				this.handleSubscribe(query);
				break;
			case UNSUBSCRIBE:
				this.handleUnsubscribe(query);
				break;
			case CREATE_TOPIC:
				this.handleCreateTopic(query);
				break;
			case DELETE_TOPIC:
				this.handleDeleteTopic(query);
				break;
		}
		
		// Send ACK
		Query ack = new Query(query.getClientId(), QueryType.ACK);
		this.server.getReceivers().get(query.getClientId()).getToSend().add(ack);
	}
	
	/**
	 * Main method for executing a producer action.
	 * Waits until a message is available in the queue and then sends the message.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void sendMessages() throws InterruptedException, IOException{
		
		Object obj = this.toSend.take();
		
		this.outputStream.writeObject(obj);
		
	}

	/**
	 * Handle the socket registration
	 * @throws Exception
	 */
	private void handleRegister() throws Exception {

		Query query = (Query) this.inputStream.readObject();
		
		switch(query.getType()){
			case REGISTER_SENDER:
				this.handleRegisterSender(query);
				break;
			case REGISTER_RECEIVER:
				this.handleRegisterReceiver(query);
				break;
			default:
				throw new Exception("Expecting REGISTER_CONSUMER or REGISTER_PRODUCER query.");
		}
		
	}

	/**
	 * Register this socket as a RECEIVER
	 * @param query
	 * @throws IOException
	 */
	private void handleRegisterReceiver(Query query) throws IOException {
		logger.log(Level.INFO, "Producer registered ({0})", this.id);
		this.type = ConnectionHandlerType.RECEIVER;
		
		// Send ACK
		Query ack = new Query(query.getClientId(), QueryType.REGISTER_RECEIVER_ACK);
		this.outputStream.writeObject(ack);
		
		// Register this socket in the server
		this.server.handleRegisterReceiver(query, this.id);
	}

	/**
	 * Register this socket as a SENDER
	 * @param query
	 * @throws IOException
	 */
	private void handleRegisterSender(Query query) throws IOException {
		logger.log(Level.INFO, "Consumer registered ({0})", this.id);
		this.type = ConnectionHandlerType.SENDER;
		
		// Send ACK
		Query ack = new Query(query.getClientId(), QueryType.REGISTER_SENDER_ACK);
		this.outputStream.writeObject(ack);
		
		// Register this socket in the server
		this.server.handleRegisterSender(query, this.id);
	}

	private void handleDeleteTopic(Query query) {
		// TODO Auto-generated method stub
		
	}

	private void handleCreateTopic(Query query) {
		// TODO Auto-generated method stub
		
	}

	private void handleUnsubscribe(Query query) {
		// TODO Auto-generated method stub
		
	}

	private void handleSubscribe(Query query) {
		// TODO Auto-generated method stub	
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConnectionHandlerType getType() {
		return type;
	}

	public void setType(ConnectionHandlerType type) {
		this.type = type;
	}

	public LinkedBlockingQueue<Object> getToSend() {
		return toSend;
	}

	public void setToSend(LinkedBlockingQueue<Object> toSend) {
		this.toSend = toSend;
	}
	
}
