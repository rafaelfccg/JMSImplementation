package server;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.JMSException;

import server.misc.MessageAckPair;
import server.query.MessageQuery;

public class MessageManager implements Runnable {
	
	private Server server;
	
	private volatile ConcurrentLinkedQueue<MessageAckPair> queue;
	
	private ReentrantLock lock;
	
	public MessageManager(Server server, ConcurrentLinkedQueue<MessageAckPair> queue, ReentrantLock lock) {
		super();
		this.server = server;
		this.queue = queue;
		this.lock = lock;
	}

	@Override
	public void run() {
		while(true){
			try {
				this.lock.lock();
				MessageAckPair curr = queue.peek();
				if(curr == null) continue;
				if(System.currentTimeMillis() > curr.getTimeout()){
					curr.setTimeout(System.currentTimeMillis() + 10*1000);
					curr.getMessage().getMessage().setJMSRedelivered(true);
					this.server.getReceivers().get(curr.getMessage().getClientId()).getToSend().add(curr.getMessage());
				}else{
					long time =  curr.getTimeout() - System.currentTimeMillis();
					if(time > 0)
						Thread.sleep(time);
				}
				queue.add(curr);
			}catch(NullPointerException e){ 
				queue.remove();
			}catch (JMSException |InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				this.lock.unlock();
			}

		}
	}
	
}
