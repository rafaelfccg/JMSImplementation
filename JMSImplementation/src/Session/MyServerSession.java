package Session;

import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.Session;

public class MyServerSession implements ServerSession{

	@Override
	public Session getSession() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws JMSException {
		// TODO Auto-generated method stub
		
	}
}
