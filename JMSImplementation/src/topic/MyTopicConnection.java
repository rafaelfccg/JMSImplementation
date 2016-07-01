package topic;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import connection.MyConnection;

public class MyTopicConnection extends MyConnection implements TopicConnection{

	public MyTopicConnection(String hostIp, int hostPort) {
		super(hostIp, hostPort);
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Topic arg0, String arg1, ServerSessionPool arg2, int arg3)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSession createTopicSession(boolean arg0, int arg1) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
