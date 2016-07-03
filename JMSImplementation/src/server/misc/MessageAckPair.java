package server.misc;

import javax.jms.JMSException;

import server.query.MessageQuery;

public class MessageAckPair {

	private String msgId;
	
	private MessageQuery message;
	
	private Long timeout;

	public MessageAckPair(MessageQuery message, Long timestamp) {
		super();
		this.message = message;
		this.timeout = timestamp;
	}

	public MessageQuery getMessage() {
		return message;
	}

	public void setMessage(MessageQuery message) {
		this.message = message;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timestamp) {
		this.timeout = timestamp;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public boolean equals(MessageAckPair other){
		return this.msgId.equals(other.msgId);
	}
	
}
