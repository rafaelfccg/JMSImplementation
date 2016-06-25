package topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import messages.MyMessageConsumer;

public class MyTopicSubscriber extends MyMessageConsumer implements TopicSubscriber{

	@Override
	public boolean getNoLocal() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Topic getTopic() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

}
