package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	
	private static final Logger logger = Logger.getLogger( Server.class.getName() );
	
	private ServerSocket serverSocket;
	
	public Server(int port) throws IOException{
		this.serverSocket = new ServerSocket(port);
	}
	
	public void init() throws IOException{
		
		while(true){
			
			Socket socket = this.serverSocket.accept();
			logger.log(Level.INFO, "Client connected ({0})", socket.getInetAddress());
			
			try{
				
				ConnectionHandler handler = new ConnectionHandler(socket, this);
				
				Thread thread = new Thread(handler);
				thread.start();
				
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		
	}
}
