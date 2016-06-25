package topic;

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import connection.MyConnectionFactory;

public class MyTopicConnectionFactory extends MyConnectionFactory implements TopicConnectionFactory {

	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicConnection createTopicConnection(String arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
