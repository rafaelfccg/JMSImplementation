import server.Server;

public class ServerLauncher {
	
	public static void main(String[] args) throws Exception{
		Server server = new Server(12345);
		server.init();
	}
	
}
