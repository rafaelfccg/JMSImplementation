package server.query;

import messages.MyMessage;

public class MessageQuery extends Query{

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
	
}
