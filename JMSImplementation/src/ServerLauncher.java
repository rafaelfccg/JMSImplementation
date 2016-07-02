import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import connection.MyConnectionFactory;
import server.Server;
import utils.Utils;

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
		Context ctx;
		try {
			LocateRegistry.createRegistry(1099);
			ctx = new InitialContext(Utils.enviroment());
			ctx.bind("ConnectionFactory", new MyConnectionFactory("localhost", 12345));
			Server server = new Server(12345);
			server.init();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
