package server.query;

import javax.jms.JMSException;

import messages.MyMessage;

public class MessageQuery extends Query implements Comparable<MessageQuery>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3787295028411469366L;
	private MyMessage message;
	
	public MessageQuery(String clientId, MyMessage myMessage){
		super(clientId, QueryType.MESSAGE);
		this.message = myMessage;
	}

	public MyMessage getMessage() {
		return message;
	}

	public void setMessage(MyMessage message) {
		this.message = message;
	}

	@Override
	public int compareTo(MessageQuery other) {
		MyMessage a = this.message;
		MyMessage b = other.getMessage();
		
		try {
			return (int) ((double)a.getJMSTimestamp()/(a.getJMSPriority() + 1) - (double)b.getJMSTimestamp()/(b.getJMSPriority() + 1));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
