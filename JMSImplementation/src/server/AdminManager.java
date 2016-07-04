package server;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	private TopicManager topicManager;
	
	public AdminManager(Server server) {
		super();
		this.server = server;
		this.topicManager = server.getTopicManager();
	}

	public void handle(SubscriberQuery query) throws JMSException{
		
		if(query.getTopic().equals(MyConnectionAdmin.LIST_TOPICS)){
			this.getTopicList(query);
		}else if(query.getTopic().equals(MyConnectionAdmin.TOPICS_CONSUMERS)){
			this.getTopicSubscribers(query);
		}else if(query.getTopic().equals(MyConnectionAdmin.TOPICS_STATS)){
			this.getTopicStats(query);
		}
		
	}
	
	public void getTopicList(SubscriberQuery query) throws JMSException{
		MyObjectMessage omsg = new MyObjectMessage();
		omsg.setJMSDestination(new MyTopic(query.getTopic()));
		ArrayList<Topic> arr = this.server.getTopicManager().getTopicList();
		ArrayList<Topic> filtered = filter(arr);
		
		omsg.setObject(filtered);
		MessageQuery qry = new MessageQuery("0", omsg);
		this.server.getTopicManager().addMessageToTopic(MyConnectionAdmin.LIST_TOPICS, qry);
	}
	
	public void getTopicSubscribers(SubscriberQuery query) throws JMSException{
		MyMapMessage omsg = new MyMapMessage();
		omsg.setJMSDestination(new MyTopic(query.getTopic()));
		ArrayList<Topic> arr = filter(this.server.getTopicManager().getTopicList());
		for(Topic t: arr){
			omsg.setInt(t.getTopicName(), this.server.getTopicManager().getSubscribed(t.getTopicName()).size());	
		}
		MessageQuery qry = new MessageQuery("0", omsg);
		this.server.getTopicManager().addMessageToTopic(MyConnectionAdmin.TOPICS_CONSUMERS, qry);
	}

	public void getTopicStats(SubscriberQuery query) throws JMSException{
		MyMapMessage omsg = new MyMapMessage();
		omsg.setJMSDestination(new MyTopic(query.getTopic()));
		ArrayList<Topic> arr = filter(this.server.getTopicManager().getTopicList());
		
		for(Topic t: arr){
			HashMap<String, Double> stats = new HashMap<String, Double>();
			int minutes = (int) (System.currentTimeMillis() - this.topicManager.getTopicTimestamp(t.getTopicName())/1000*60);
			stats.put("average_messages_per_minute", (double) (this.topicManager.getTopicTotalMessages(t.getTopicName()) / minutes) );
			stats.put("total_messages", (double) this.topicManager.getTopicTotalMessages(t.getTopicName()) );
			omsg.setObject(t.getTopicName(), stats);	
		}
		MessageQuery qry = new MessageQuery("0", omsg);
		this.server.getTopicManager().addMessageToTopic(MyConnectionAdmin.TOPICS_STATS, qry);
	}
	
	public ArrayList<Topic> filter(ArrayList<Topic> arr) throws JMSException{
		ArrayList<Topic> filtered = new ArrayList<Topic>();
		for(Topic t: arr){
			if(t.getTopicName().equals("/") 
					|| t.getTopicName().startsWith("admin_")) continue;
			filtered.add(t);
		}
		return filtered;
	}
	
}
