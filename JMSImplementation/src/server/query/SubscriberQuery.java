package server.query;

public class SubscriberQuery extends AbstractQuery {
	
	private int subscriberId;
	private String clientId;
	private String topic;

	public SubscriberQuery(int subscriberId, String topic) {
		super();
		this.setType(QueryType.SUBSCRIBE);
		this.subscriberId = subscriberId;
		this.topic = topic;
	}

	public SubscriberQuery(String clientID, String topicName) {
		super();
		this.setClientId(clientID);
		this.topic  = topicName;
		this.setType(QueryType.SUBSCRIBE);
	}

	public int getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(int subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
