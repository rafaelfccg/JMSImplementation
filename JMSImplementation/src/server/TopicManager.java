package server;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicManager {

	private class Node {
		
		private String name;
		
		private ArrayList<String> subscribed;
		
		private HashMap<String, Node> children;
		
		public Node(String name){
			this.name = name;
			this.subscribed = new ArrayList<String>();
			this.children = new HashMap<String, Node>();
		}

		public ArrayList<String> getSubscribed() {
			return subscribed;
		}

		public void setSubscribed(ArrayList<String> subscribed) {
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
		
	}
	
	private Node root;
	
	public TopicManager(){
		this.root = new Node("/");
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
	
	public ArrayList<String> getSubscribed(String path){
		ArrayList<String> subscribed = new ArrayList<String>();
		
		String[] components = this.getComponents(path);
		
		Node curr = root;
		subscribed.addAll(curr.getSubscribed());
		
		for(String component : components){
			curr = curr.getChildren().get(component);
			subscribed.addAll(curr.getSubscribed());
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
			if(!curr.getChildren().containsKey(component)) curr.getChildren().put(component, new Node(component));
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
		str += node.getName() + ": " + String.join(", ", node.getSubscribed()) + "\n";
		for(Node child: node.getChildren().values()){
			str += dumpAux(child, level + 1);
		}
		return str;
	}
	
}
