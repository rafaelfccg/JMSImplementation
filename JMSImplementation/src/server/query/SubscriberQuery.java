package server.query;

public class SubscriberQuery extends AbstractQuery {
	
	private int subscriberId;
	
	private String topic;

	public SubscriberQuery(int subscriberId, String topic) {
		super();
		this.setType(QueryType.SUBSCRIBE);
		this.subscriberId = subscriberId;
		this.topic = topic;
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
