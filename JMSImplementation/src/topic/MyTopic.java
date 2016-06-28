package topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Topic;

public class MyTopic implements Topic {

	private String name;
	public MyTopic(String name){
		this.name = name;
	}
	@Override
	public String getTopicName() throws JMSException {
		return name;
	}
}
