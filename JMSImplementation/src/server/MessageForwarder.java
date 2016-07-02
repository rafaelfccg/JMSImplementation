package server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

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
				
				Object message = this.topicManager.getMessageToSend(topic);
				
				if(message == null) continue;
				
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
