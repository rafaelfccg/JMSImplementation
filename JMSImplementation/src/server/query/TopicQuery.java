package server.query;

public class TopicQuery extends Query {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7838204593160234378L;
	private String name;

	public TopicQuery(String clientId, String name) {
		super(clientId, QueryType.CREATE_TOPIC);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
