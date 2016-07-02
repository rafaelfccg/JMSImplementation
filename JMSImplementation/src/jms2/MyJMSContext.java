package jms2;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;

import connection.MyConnectionSendMessage;

public class MyJMSContext implements JMSContext{
	
	private Connection connection;
	private Session session;
	private boolean autoStart;
	
	@Override
	public void acknowledge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void commit() {
		try {
			this.session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public QueueBrowser createBrowser(Queue arg0) {
		try {
			return this.session.createBrowser(arg0);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public QueueBrowser createBrowser(Queue arg0, String arg1) {
		try {
			return this.session.createBrowser(arg0,arg1);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BytesMessage createBytesMessage() {
		try {
			return this.session.createBytesMessage();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JMSConsumer createConsumer(Destination arg0) {
		try {
			return new MyJMSConsumer(this.session.createConsumer(arg0));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JMSConsumer createConsumer(Destination arg0, String arg1) {
		try {
			return new MyJMSConsumer(this.session.createConsumer(arg0,arg1));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JMSConsumer createConsumer(Destination arg0, String arg1, boolean arg2) {
		try {
			return new MyJMSConsumer(this.session.createConsumer(arg0,arg1,arg2));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JMSContext createContext(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JMSConsumer createDurableConsumer(Topic arg0, String arg1) {
		try {
			return new MyJMSConsumer(this.session.createDurableSubscriber(arg0,arg1));
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public JMSConsumer createDurableConsumer(Topic arg0, String arg1, String arg2, boolean arg3) {
			throw new JMSRuntimeException("Method not Implemented");
	}

	@Override
	public MapMessage createMapMessage() {
		try {
			return this.session.createMapMessage();
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public Message createMessage() {
		try {
			return this.session.createMessage();
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public ObjectMessage createObjectMessage() {
		try {
			return this.session.createObjectMessage();
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable arg0) {
		try {
			return this.session.createObjectMessage(arg0);
		} catch (JMSException e) {
			throw new JMSRuntimeException(e.getMessage());
		}
	}

	@Override
	public JMSProducer createProducer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Queue createQueue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JMSConsumer createSharedConsumer(Topic arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JMSConsumer createSharedConsumer(Topic arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JMSConsumer createSharedDurableConsumer(Topic arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JMSConsumer createSharedDurableConsumer(Topic arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StreamMessage createStreamMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemporaryQueue createTemporaryQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemporaryTopic createTemporaryTopic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextMessage createTextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextMessage createTextMessage(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Topic createTopic(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAutoStart() {
		return this.getAutoStart();
	}

	@Override
	public String getClientID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExceptionListener getExceptionListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionMetaData getMetaData() {
		// TODO Auto-generated method stub
		try {
			return this.connection.getMetaData();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getSessionMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getTransacted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoStart(boolean arg0) {
		this.autoStart = arg0;
	}

	@Override
	public void setClientID(String arg0) {
		// TODO Auto-generated method stub
		try {
			connection.setClientID(arg0);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void setExceptionListener(ExceptionListener arg0) {
		// TODO Auto-generated method stub
		try {
			connection.setExceptionListener(arg0);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			connection.stop();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void unsubscribe(String arg0) {
		// TODO Auto-generated method stub
		try {
			session.unsubscribe(arg0);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
