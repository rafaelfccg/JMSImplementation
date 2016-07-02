package server.query;

public class AckQuery extends Query {
	
	private static final long serialVersionUID = 9144959361556859970L;
	private String messageID;
	
	public AckQuery(String clientId, String msgId) {
		super(clientId, QueryType.ACK);
		this.setMessageID(msgId);
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

}
