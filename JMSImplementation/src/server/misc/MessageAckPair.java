package server.misc;

import server.query.MessageQuery;

public class MessageAckPair {

	private MessageQuery message;
	
	private Long timestamp;

	public MessageAckPair(MessageQuery message, Long timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

	public MessageQuery getMessage() {
		return message;
	}

	public void setMessage(MessageQuery message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
