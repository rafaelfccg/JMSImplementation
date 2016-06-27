package session;

import javax.jms.Message;

public interface SessionMessageReceiverListener {
	public void onMessageReceived(Message message);

}
