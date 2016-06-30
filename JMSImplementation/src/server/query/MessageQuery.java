package server.query;

import messages.MyMessage;

public class MessageQuery extends Query{

	private MyMessage message;
	
	public MessageQuery(String clientId, MyMessage message){
		super(clientId, QueryType.MESSAGE);
		this.message = message;
	}

	public MyMessage getMessage() {
		return message;
	}

	public void setMessage(MyMessage message) {
		this.message = message;
	}
	
}
