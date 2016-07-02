import server.Server;

/**
 * To use the JNDI service:
 * Hashtable<String, String> env = new Hashtable();
 * env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
 * env.put(Context.PROVIDER_URL, "rmi://127.0.0.1:1099");
 * Context context = new InitialContext(env);
 *
 * All binded objects MUST implement Remote and Serializable
 *
 */

public class ServerLauncher {
	
	public static void main(String[] args) throws Exception{
		
		Server server = new Server(12345);
		server.init();
		
	}
	
}
