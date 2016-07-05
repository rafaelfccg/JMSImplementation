package server;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.JMSException;

import server.misc.MessageAckPair;
import server.query.MessageQuery;

public class MessageManager implements Runnable {
	
	private Server server;
	
	private String clientId;
	
	private ReentrantLock lock;
	
	public MessageManager(Server server, String clientId, ReentrantLock lock) {
		super();
		this.server = server;
		this.clientId = clientId;
		this.lock = lock;
	}

	@Override
	public void run() {
		while(true){
			try {
				this.lock.lock();
				ConcurrentLinkedQueue<MessageAckPair> queue = this.server.getReceivers().get(this.clientId).getWaitingAck();
				MessageAckPair curr = queue.poll();
				if(curr == null) continue;
				if(System.currentTimeMillis() > curr.getTimeout()){
					System.out.println("Timeout [clientid: " + this.clientId + "] [id: " + curr.getMsgId() + "] [size: " + this.server.getReceivers().get(this.clientId).getWaitingAck().size() + "]");
					curr.setTimeout(System.currentTimeMillis() + 10*1000);
					curr.getMessage().getMessage().setJMSRedelivered(true);
					this.server.getReceivers().get(this.clientId).getToSend().add(curr.getMessage());
				}else{
					long time =  curr.getTimeout() - System.currentTimeMillis();
					if(time > 0){
						
						queue.add(curr);
						System.out.println("NO Timeout [clientid: " + this.clientId + "] [id: " + curr.getMsgId() + "] [size: " + this.server.getReceivers().get(this.clientId).getWaitingAck().size() + "]");
						
						this.lock.unlock();
						Thread.sleep(time);
					}
				}
			}catch (JMSException |InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(this.lock.isHeldByCurrentThread()) this.lock.unlock();
			}

		}
	}
	
}
