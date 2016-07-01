package utils;

import javax.jms.JMSException;

public class Utils {
	public static void raise(Exception exception) throws JMSException {
		JMSException error = new JMSException(exception.getMessage());
		error.setLinkedException(exception);
		throw error;
	}
}
