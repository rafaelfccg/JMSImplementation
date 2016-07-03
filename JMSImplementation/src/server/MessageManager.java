package server;

import java.util.concurrent.ConcurrentLinkedQueue;

import server.misc.MessageAckPair;
import server.query.MessageQuery;

public class MessageManager implements Runnable {
	
	private Server server;
	
	private ConcurrentLinkedQueue<MessageAckPair> queue;
	
	public MessageManager(Server server, ConcurrentLinkedQueue<MessageAckPair> queue) {
		super();
		this.server = server;
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true){
			MessageAckPair curr = queue.peek();
			if(curr == null) continue;
			if(curr.getTimestamp() - System.currentTimeMillis() > 10*1000){
				queue.poll();
				curr.setTimestamp(System.currentTimeMillis());
				queue.add(curr);
				this.server.getReceivers().get(curr.getMessage().getClientId()).getToSend().add(curr.getMessage());
			}else{
				try {
					Thread.sleep(curr.getTimestamp() - System.currentTimeMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
