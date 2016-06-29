package server.query;

public class SubscriberQuery extends Query {
	
	private int subscriberId;
	private String topic;

	public SubscriberQuery(String clientId, int subscriberId, String topic) {
		super(clientId, QueryType.SUBSCRIBE);
		this.subscriberId = subscriberId;
		this.topic = topic;
	}

	public SubscriberQuery(String clientId, String topicName) {
		super(clientId, QueryType.SUBSCRIBE);
		this.setClientId(clientId);
		this.topic  = topicName;
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

}
