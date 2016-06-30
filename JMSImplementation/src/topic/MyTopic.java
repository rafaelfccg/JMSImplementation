package topic;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Topic;

public class MyTopic implements Topic, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5010233988379309842L;
	private String name;
	public MyTopic(String name){
		this.name = name;
	}
	@Override
	public String getTopicName() throws JMSException {
		return name;
	}
}
