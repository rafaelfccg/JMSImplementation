package utils;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.naming.Context;

public class Utils {
	
	public static final String LIST_TOPIC = "myJMSListTopics";
	public static void raise(Exception exception) throws JMSException {
		JMSException error = new JMSException(exception.getMessage());
		error.setLinkedException(exception);
		throw error;
	}
	public static Hashtable<String,Object> enviroment(){
		Hashtable<String,Object> env = new Hashtable<String,Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
			        "com.sun.jndi.rmi.registry.RegistryContextFactory");
		env.put(Context.PROVIDER_URL, "rmi://localhost:1099");
		return env;
	}
}
