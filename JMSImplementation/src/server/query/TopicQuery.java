package server.query;

public class TopicQuery extends AbstractQuery {
	
	private String name;

	public TopicQuery(String name) {
		super();
		this.setType(QueryType.CREATE_TOPIC);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
