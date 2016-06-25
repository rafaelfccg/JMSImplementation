package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRequestHandler {

	private String hostname;
	private int port;
	
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	
	public ClientRequestHandler(String hostname, int port) throws UnknownHostException, IOException{
		this.hostname = hostname;
		this.port = port;
	}
	
	public byte[] sendAndReceive(byte[] bytes) throws IOException{
		send(bytes);
		return receive();
	}
	 
	public void send(byte[] bytes) throws IOException{
		this.socket = new Socket(this.hostname, this.port);
		this.output = new DataOutputStream(this.socket.getOutputStream());
		this.input = new DataInputStream(this.socket.getInputStream());
		this.output.writeInt(bytes.length);
		this.output.write(bytes);
	}
	
	public byte[] receive() throws IOException{
		int size = this.input.readInt();
		byte[] bytes = new byte[size];
		this.input.readFully(bytes);
		this.socket.close();
		return bytes;
	}
	
}
