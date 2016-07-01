package topic;

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import connection.MyConnectionFactory;

public class MyTopicConnectionFactory extends MyConnectionFactory implements TopicConnectionFactory {

	public MyTopicConnectionFactory(String ip, int port) {
		super(ip, port);
	}

	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		return new MyTopicConnection(this.providerIp,this.providerPort);
	}

	@Override
	public TopicConnection createTopicConnection(String arg0, String arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
