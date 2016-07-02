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
	
	String name;
	
	public MyTopicSubscriber(Destination destination, String selector, boolean noLocal,
			SessionConsumerOperations owner) {
		super(destination, selector, noLocal, owner);
	}
	
	public MyTopicSubscriber(Destination destination,String name, String selector,   boolean noLocal,
			SessionConsumerOperations owner) {
		super(destination, selector, noLocal, owner);
		this.name= name;
	}

	@Override
	public boolean getNoLocal() throws JMSException {
		return noLocal;
	}

	@Override
	public Topic getTopic() throws JMSException {
		return (Topic)this.getDestination();
	}

}
