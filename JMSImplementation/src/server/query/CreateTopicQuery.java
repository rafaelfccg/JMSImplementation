package server.query;

import javax.jms.Topic;

public class CreateTopicQuery extends Query{
	
	private static final long serialVersionUID = 6096918632067703725L;
	
	private Topic topic;
	
	public CreateTopicQuery(String clientId) {
		super(clientId, QueryType.CREATE_TOPIC);
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
