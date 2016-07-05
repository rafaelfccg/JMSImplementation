package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.jms.Topic;

import server.query.MessageQuery;
import topic.MyTopic;

public class TopicManager {

	private class Node {
		
		private String name;
		
		private ConcurrentLinkedQueue<String> subscribed;
		
		private HashMap<String, Node> children;
		
		private Node parent;
		
		private PriorityBlockingQueue<MessageQuery> messages;
		
		private int totalMessages;
		
		private long timestamp;
		
		public Node(String name, Node parent){
			this.name = name;
			this.parent = parent;
			this.subscribed = new ConcurrentLinkedQueue<String>();
			this.children = new HashMap<String, Node>();
			this.messages = new PriorityBlockingQueue<MessageQuery>();
			this.totalMessages = 0;
			this.timestamp = System.currentTimeMillis();
		}

		public ConcurrentLinkedQueue<String> getSubscribed() {
			return subscribed;
		}

		public void setSubscribed(ConcurrentLinkedQueue<String> subscribed) {
			this.subscribed = subscribed;
		}

		public HashMap<String, Node> getChildren() {
			return children;
		}

		public void setChildren(HashMap<String, Node> children) {
			this.children = children;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public PriorityBlockingQueue<MessageQuery> getMessages() {
			return messages;
		}

		public void setMessages(PriorityBlockingQueue<MessageQuery> messages) {
			this.messages = messages;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public int getTotalMessages() {
			return totalMessages;
		}

		public void setTotalMessages(int totalMessages) {
			this.totalMessages = totalMessages;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
	}
	
	private Node root;
	
	private LinkedBlockingQueue<String> lastUpdatedTopics;
	
	public TopicManager(){
		this.root = new Node("/", null);
		this.lastUpdatedTopics = new LinkedBlockingQueue<String>();
	}
	
	private String[] getComponents(String path){
		if(path.charAt(0) == '/') path = path.substring(1);
		return path.split("/");
	}
	
	private Node getNode(String path){
		String[] components = this.getComponents(path);
		
		Node curr = root;
		
		for(String component : components){
			if(!curr.getChildren().containsKey(component)) return null;
			curr = curr.getChildren().get(component);
		}	
		
		return curr;
	}
	
	public int getTopicTotalMessages(String path){
		return this.getNode(path).getTotalMessages();
	}
	
	public long getTopicTimestamp(String path){
		return this.getNode(path).getTimestamp();
	}
	
	public Object getMessageToSend(String path){
		return this.getNode(path).getMessages().poll();
	}
	
	public Object getAllMessages(String path){
		return this.getNode(path).getMessages();
	}
	
	public void addMessageToTopic(String path, MessageQuery message){
		if(!this.exists(path)) this.create(path);
		Node node = this.getNode(path);
		this.lastUpdatedTopics.add(path);
		node.getMessages().add(message);
		node.setTotalMessages(node.getTotalMessages()+1);
	}
	
	public ArrayList<String> getSubscribed(String path){
		ArrayList<String> subscribed = new ArrayList<String>();
		
		Node curr = this.getNode(path);
		
		while(curr != null){
			subscribed.addAll(curr.getSubscribed());
			curr = curr.getParent();
		}
		
		return subscribed;
	}
	
	
	public void subscribe(String path, String clientId){
		if(!this.exists(path)) this.create(path);
		this.getNode(path).getSubscribed().add(clientId);
	}
	
	public void unsubscribe(String path, String clientId){
		if(this.exists(path))
			this.getNode(path).getSubscribed().remove(clientId);
	}
	
	public void create(String path){

		String[] components = this.getComponents(path);
		
		Node curr = root;
		
		for(String component : components){
			if(!curr.getChildren().containsKey(component)) curr.getChildren().put(component, new Node(component, curr));
			curr = curr.getChildren().get(component);
		}	
	}
	

	public void delete(String path){

		String[] components = this.getComponents(path);
		
		Node prev = null;
		Node curr = root;
		
		for(String component : components){
			if(!curr.getChildren().containsKey(component)) return;
			prev = curr;
			curr = curr.getChildren().get(component);
		}	
		
		prev.getChildren().remove(components[components.length-1]);
	}
	
	public boolean exists(String path){
		return this.getNode(path) != null;
	}
	
	public String dump(){
		return dumpAux(this.root, 0);
	}
	
	private String dumpAux(Node node, int level){
		String str = "";
		for(int i=0; i < level*4; i++) str += " ";
		str += node.getName() + "(" + node.getSubscribed().size() + "): " + String.join(", ", node.getSubscribed()) + "\n";
		for(Node child: node.getChildren().values()){
			str += dumpAux(child, level + 1);
		}
		return str;
	}

	public LinkedBlockingQueue<String> getLastUpdatedTopics() {
		return lastUpdatedTopics;
	}

	public void setLastUpdatedTopics(LinkedBlockingQueue<String> lastUpdatedTopics) {
		this.lastUpdatedTopics = lastUpdatedTopics;
	}
	
	public String getFullName(Node node){
		String str = node.getName();
		node = node.getParent();
		while(node != null && node != root){
			str = node.getName() + "/" + str;
			node = node.getParent();
		}
		return str;
	}
	
	public ArrayList<Topic> getTopicList(){
		ArrayList<Topic> list = new ArrayList<Topic>();
		ArrayList<Node> nodeList  = new ArrayList<Node>();
		nodeList.add(this.root);
		for(int i = 0; i< nodeList.size(); i++){
			HashMap<String,Node> children = nodeList.get(i).getChildren();
			Set<Entry<String,Node>> set  = children.entrySet();
			for(Entry<String,Node> entry : set){
				nodeList.add(entry.getValue());
			}
			list.add(new MyTopic(getFullName(nodeList.get(i))));
		}
		
		return list;
	}
	
}
