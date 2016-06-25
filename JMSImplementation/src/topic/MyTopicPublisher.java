package topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.TopicPublisher;

import messages.MyMessageProducer;

public class MyTopicPublisher extends MyMessageProducer implements TopicPublisher{

	@Override
	public Topic getTopic() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void publish(Message arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(Topic arg0, Message arg1) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(Message arg0, int arg1, int arg2, long arg3) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(Topic arg0, Message arg1, int arg2, int arg3, long arg4) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	
}
