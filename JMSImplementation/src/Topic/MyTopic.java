package Topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Topic;

public class MyTopic implements Topic {

	private String name;
	
	@Override
	public String getTopicName() throws JMSException {
		return name;
	}
}
