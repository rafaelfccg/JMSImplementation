package jndi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import connection.MyConnectionFactory;
import utils.Utils;

public class NamingService {
	public static void main(String[] args) {
		Context ctx;
		
		try {
			LocateRegistry.createRegistry(1099);
			ctx = new InitialContext(Utils.enviroment());
			ctx.bind("ConnectionFactory", new MyConnectionFactory("localhost", 12345));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
