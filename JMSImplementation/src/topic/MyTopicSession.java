package topic;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import connection.MyConnectionSendMessage;
import session.MySession;

public class MyTopicSession extends MySession implements TopicSession{

	public MyTopicSession(boolean trans, int ack, MyConnectionSendMessage connection) {
		super(trans, ack, connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TopicPublisher createPublisher(Topic arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSubscriber createSubscriber(Topic arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSubscriber createSubscriber(Topic arg0, String arg1, boolean arg2) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

			
}
