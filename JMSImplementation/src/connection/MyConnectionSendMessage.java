package connection;

import java.io.IOException;

import javax.jms.Message;

public interface MyConnectionSendMessage {
	public void send(Message my) throws IOException;
}
