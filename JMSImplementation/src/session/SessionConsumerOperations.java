package session;

import messages.MyMessageConsumer;

public interface SessionConsumerOperations {
	public void closeConsumer(MyMessageConsumer consumer);
}
