package server.query;

import java.io.Serializable;

public class Query implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2396198797934569671L;

	private String clientId;
	
	private QueryType type;
	
	public Query(String clientId, QueryType type) {
		super();
		this.clientId = clientId;
		this.type = type;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public QueryType getType() {
		return type;
	}

	public void setType(QueryType type) {
		this.type = type;
	}
	
}
