package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.query.AbstractQuery;

public class ConnectionHandler implements Runnable{
	
	private Server server;
	
	private Socket socket;
	
	private ObjectOutputStream outputStream;
	
	private ObjectInputStream inputStream;
	
	private boolean running;
	
	public ConnectionHandler(Socket socket, Server server) throws IOException{
		this.socket = socket;
		this.server = server;
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		this.inputStream = new ObjectInputStream(socket.getInputStream());
		this.running = true;
	}

	@Override
	public void run() {

		while(running){
			try {
				
				AbstractQuery query = (AbstractQuery) this.inputStream.readObject();

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
				
			} catch (Exception e) {
				this.running = false;
			}
		}
		
	}

	private void handleDeleteTopic(AbstractQuery query) {
		// TODO Auto-generated method stub
		
	}

	private void handleCreateTopic(AbstractQuery query) {
		// TODO Auto-generated method stub
		
	}

	private void handleUnsubscribe(AbstractQuery query) {
		// TODO Auto-generated method stub
		
	}

	private void handleSubscribe(AbstractQuery query) {
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
	
}
