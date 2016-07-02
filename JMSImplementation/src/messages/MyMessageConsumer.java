package messages;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

import session.SessionConsumerOperations;
import utils.Utils;

public class MyMessageConsumer implements MessageConsumer, MessageListener {
	
	private Destination destination;
	private MessageListener messageListener;
	private Queue<Message> messageQueue;
	private Message lastMessage;
	private String selector;
	private ReentrantLock lock;
	private AtomicInteger operationsInProgress;
	private boolean closed;
	protected boolean noLocal;
	private SessionConsumerOperations owner;
	
	public MyMessageConsumer(Destination destination, String selector, boolean noLocal,SessionConsumerOperations owner){
		this.owner = owner;
		this.destination = destination;
		this.operationsInProgress = new AtomicInteger(0);
		this.messageQueue = new LinkedList<Message>(); 
		this.selector = selector;
		this.noLocal = noLocal;
		this.lock = new ReentrantLock();
	}
	@Override
	public void close() throws JMSException {
		do{
			lock.lock();
			if(this.operationsInProgress.get() != 0){
				lock.unlock();
			}
		}while(this.operationsInProgress.get() != 0);
		this.closed = true;
		messageQueue.clear();
		messageQueue = null;
		this.messageListener = null;
		owner.closeConsumer(this);
		lock.unlock();
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return messageListener;
	}

	@Override
	public String getMessageSelector() throws JMSException {
		return selector;
	}
	public void isOpen() throws JMSException{
		try{
			this.lock.lock();
			if(closed){
				throw new JMSException("Consumer closed");
			}
		}finally{
			this.lock.unlock();
		}
	}
	@Override
	public Message receive() throws JMSException {
		isOpen();
		this.operationsInProgress.incrementAndGet();
		lock.lock();
		Message msg = null;
		try {
			if(this.messageQueue.isEmpty()){
				lock.wait();
				if(this.messageQueue.isEmpty()){
					return lastMessage;
				}else {
					msg = this.messageQueue.remove();
					this.owner.received(msg);
					return msg;
				}
			}
		} catch (InterruptedException e) {
			Utils.raise(e);
		}finally{
			lock.unlock();
			this.operationsInProgress.decrementAndGet();
		}
		return msg;
	}

	@Override
	public Message receive(long arg0) throws JMSException {
		isOpen();
		this.operationsInProgress.incrementAndGet();
		lock.lock();
		Message msg = null;
		try {
			if(this.messageQueue.isEmpty()){
				lock.wait(arg0);
				//double verification
				if(this.messageQueue.isEmpty()){
					return lastMessage;
				}else {
					msg = this.messageQueue.remove();
					this.owner.received(msg);
					return msg;
				}
			}
			
		} catch (InterruptedException e) {
			Utils.raise(e);
		}finally{
			lock.unlock();
			this.operationsInProgress.decrementAndGet();
		}
		
		return msg;
	}

	@Override
	public Message receiveNoWait() throws JMSException {
		isOpen();
		this.operationsInProgress.incrementAndGet();
		Message msg = null;
		boolean locked = lock.tryLock();
		if(locked){
			if (this.messageQueue.isEmpty()) {
				return msg;
			}else{
				msg = this.messageQueue.remove();
				this.owner.received(msg);
			}
			this.lock.unlock();
		}
		this.operationsInProgress.decrementAndGet();
		return msg;
	}

	@Override
	public void setMessageListener(MessageListener arg0) throws JMSException {
		messageListener = arg0;
	}

	@Override
	public void onMessage(Message message) {  
		try {
			this.lock.lock();
			if(this.closed)return;
        	this.messageQueue.add(message);
        	this.lastMessage = message;
            if (messageListener != null) {
            	messageListener.onMessage(message);
            	this.owner.received(this.lastMessage);
            	this.messageQueue.remove();
            } 
            
        } catch (Throwable exception) {
        	exception.printStackTrace();
        }finally{
        	if(lock.isLocked()){
        		this.lock.notify();
        		this.lock.unlock();
        	}
        }
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
}
