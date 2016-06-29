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
	 * Map of ClientId -> ConnectionHandler for the sender sockets
	 * Clients only send messages, Server only listens for messages
	 */
	private HashMap<String, ConnectionHandler> senders;
	
	/**
	 * Map of ClientId -> ConnectionHandler for the receiver sockets
	 * Clients only receive messages, Server only sends for messages
	 */
	private HashMap<String, ConnectionHandler> receivers;
	
	public Server(int port) throws IOException{
		this.serverSocket = new ServerSocket(port);
		this.handlers = new HashMap<Integer, ConnectionHandler>();
		this.senders = new HashMap<String, ConnectionHandler>();
		this.receivers = new HashMap<String, ConnectionHandler>();
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

	public void handleRegisterReceiver(Query query, int handlerId){
		receivers.put(query.getClientId(), handlers.get(handlerId));
	}
	
	public void handleRegisterSender(Query query, int handlerId){
		senders.put(query.getClientId(), handlers.get(handlerId));
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

	public HashMap<String, ConnectionHandler> getSenders() {
		return senders;
	}

	public void setSenders(HashMap<String, ConnectionHandler> senders) {
		this.senders = senders;
	}

	public HashMap<String, ConnectionHandler> getReceivers() {
		return receivers;
	}

	public void setReceivers(HashMap<String, ConnectionHandler> receivers) {
		this.receivers = receivers;
	}

}
