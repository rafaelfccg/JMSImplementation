package server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import server.query.MessageQuery;

public class MessageForwarder implements Runnable {
	
	private TopicManager topicManager;
	
	private Server server;
	
	public MessageForwarder(TopicManager topicManager, Server server) {
		super();
		this.topicManager = topicManager;
		this.server = server;
	}

	@Override
	public void run() {

		while(true){
			try {
				
				String topic = this.topicManager.getLastUpdatedTopics().take();
				
				MessageQuery message = (MessageQuery) this.topicManager.getMessageToSend(topic);
				
				if(message == null) continue;
				
				if(!message.getMessage().isAlive()){
					System.out.println("MESSAGE TOO OLD");
					continue;
				}
				
				ArrayList<String> subscribed = this.topicManager.getSubscribed(topic);
				
				for(String clientId : subscribed){
					this.server.getReceivers().get(clientId).getToSend().put(message);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}

}
