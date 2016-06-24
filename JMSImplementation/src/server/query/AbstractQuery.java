package server.query;

import java.io.Serializable;

public abstract class AbstractQuery implements Serializable {
	
	private int sessionId;
	
	private QueryType type;
	
	public QueryType getType() {
		return type;
	}

	public void setType(QueryType type) {
		this.type = type;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
}
