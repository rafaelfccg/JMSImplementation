package utils;

import java.io.IOException;

import javax.jms.JMSException;

public class Utils {
	public static void raise(IOException exception) throws JMSException {
		JMSException error = new JMSException(exception.getMessage());
		error.setLinkedException(exception);
		throw error;
	}
}
