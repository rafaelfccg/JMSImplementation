package server.query;

public class UnsubscriberQuery extends Query {

	private static final long serialVersionUID = 1537903482432740189L;
	private String topic;

	public UnsubscriberQuery(String clientId, String topicName) {
		super(clientId, QueryType.SUBSCRIBE);
		this.setClientId(clientId);
		this.setTopic(topicName);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
