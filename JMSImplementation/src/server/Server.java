package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.query.Query;
import server.query.SubscriberQuery;

public class Server {
	
	private static final Logger logger = Logger.getLogger( Server.class.getName() );
	
	private ServerSocket serverSocket;
	
	private int nextHandlerId = 0;
	
	/**
	 * Map of HandlerId -> ConnectionHandler
	 */
	private HashMap<Integer, ConnectionHandler> handlers;
	
	/**
	 * Map of ClientId -> ConnectionHandler for the consumer sockets
	 */
	private HashMap<String, ConnectionHandler> consumers;
	
	/**
	 * Map of ClientId -> ConnectionHandler for the producers sockets
	 */
	private HashMap<String, ConnectionHandler> producers;
	
	public Server(int port) throws IOException{
		this.serverSocket = new ServerSocket(port);
		this.handlers = new HashMap<Integer, ConnectionHandler>();
		this.consumers = new HashMap<String, ConnectionHandler>();
		this.producers = new HashMap<String, ConnectionHandler>();
	}
	
	public void init() throws IOException{
		
		while(true){
			
			Socket socket = this.serverSocket.accept();
			
			logger.log(Level.INFO, "Client connected ({0})", socket.getInetAddress());
			
			try{
				
				ConnectionHandler handler = new ConnectionHandler(this.nextHandlerId, socket, this);
				
				handlers.put(this.nextHandlerId, handler);
				
				Thread thread = new Thread(handler);
				thread.start();
				
				this.nextHandlerId++;
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}

	public void handleRegisterConsumer(Query query, int handlerId){
		consumers.put(query.getClientId(), handlers.get(handlerId));
	}
	
	public void handleRegisterProducer(Query query, int handlerId){
		producers.put(query.getClientId(), handlers.get(handlerId));
	}

	public void handleSubscribe(SubscriberQuery query, int handlerId) {
		
		logger.log(Level.INFO, "Client subscribed to topic {0}", query.getTopic());
		
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getNextHandlerId() {
		return nextHandlerId;
	}

	public void setNextHandlerId(int nextHandlerId) {
		this.nextHandlerId = nextHandlerId;
	}

	public HashMap<Integer, ConnectionHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(HashMap<Integer, ConnectionHandler> handlers) {
		this.handlers = handlers;
	}

	public HashMap<String, ConnectionHandler> getConsumers() {
		return consumers;
	}

	public void setConsumers(HashMap<String, ConnectionHandler> consumers) {
		this.consumers = consumers;
	}

	public HashMap<String, ConnectionHandler> getProducers() {
		return producers;
	}

	public void setProducers(HashMap<String, ConnectionHandler> producers) {
		this.producers = producers;
	}
	
}
