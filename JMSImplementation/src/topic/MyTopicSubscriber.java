package topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import messages.MyMessageConsumer;
import session.SessionConsumerOperations;

public class MyTopicSubscriber extends MyMessageConsumer implements TopicSubscriber{

	public MyTopicSubscriber(Destination destination, String selector, boolean noLocal,
			SessionConsumerOperations owner) {
		super(destination, selector, noLocal, owner);
		// TODO Auto-generated constructor stub
	}

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
