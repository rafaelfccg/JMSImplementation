package server;

import java.util.ArrayList;

import javax.jms.JMSException;
import javax.jms.Topic;

import connection.MyConnectionAdmin;
import messages.MyMapMessage;
import messages.MyObjectMessage;
import server.query.MessageQuery;
import server.query.SubscriberQuery;
import topic.MyTopic;

public class AdminManager {

	private Server server;
	
	public AdminManager(Server server) {
		super();
		this.server = server;
	}

	public void handle(SubscriberQuery query) throws JMSException{
		
		if(query.getTopic().equals(MyConnectionAdmin.LIST_TOPICS)){
			MyObjectMessage omsg = new MyObjectMessage();
			omsg.setJMSDestination(new MyTopic(query.getTopic()));
			ArrayList<Topic> arr = this.server.getTopicManager().getTopicList();
			ArrayList<Topic> filtered = new ArrayList<Topic>();
			for(Topic t: arr){
				if(t.getTopicName().equals("/") 
						|| t.getTopicName().equals(MyConnectionAdmin.LIST_TOPICS)) continue;
				System.out.println(t.getTopicName());
				filtered.add(t);
			}
			omsg.setObject(filtered);
			MessageQuery qry = new MessageQuery("0", omsg);
			this.server.getTopicManager().addMessageToTopic(MyConnectionAdmin.LIST_TOPICS, qry);
		}else if(query.getTopic().equals(MyConnectionAdmin.TOPICS_CONSUMERS)){
			MyMapMessage omsg = new MyMapMessage();
			omsg.setJMSDestination(new MyTopic(query.getTopic()));
			ArrayList<Topic> arr = this.server.getTopicManager().getTopicList();
			ArrayList<Topic> filtered = new ArrayList<Topic>();
			for(Topic t: arr){
				if(t.getTopicName().equals("/") 
						|| t.getTopicName().startsWith("admin_")) continue;
				filtered.add(t);
				omsg.setInt(t.getTopicName(), this.server.getTopicManager().getSubscribed(t.getTopicName()).size());	
			}
			MessageQuery qry = new MessageQuery("0", omsg);
			this.server.getTopicManager().addMessageToTopic(MyConnectionAdmin.TOPICS_CONSUMERS, qry);
		}
		
	}
	
}
