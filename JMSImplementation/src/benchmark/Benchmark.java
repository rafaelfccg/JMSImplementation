package benchmark;

import java.util.ArrayList;

public class Benchmark {

	public static final int PRODUCERS = 1;
	
	public static final int CONSUMERS = 5;
	
	public static final int MESSAGES = 30;
	
	public static void main(String[] args) throws InterruptedException{
		
		System.out.println("Initializing consumers");
		
		ArrayList<Thread> consumers = new ArrayList<Thread>();
		
		for(int i=0; i < CONSUMERS; i++){
			Thread t = new Thread(new ConsumerThread());
			t.start();
			consumers.add(t);
		}
		
		Thread.sleep(5000);
		
		System.out.println("Initializing producers");
		
		ArrayList<Thread> producers = new ArrayList<Thread>();
		
		for(int i=0; i < PRODUCERS; i++){
			Thread t = new Thread(new ProducerThread());
			t.start();
			producers.add(t);
		}
		
		for(Thread t: consumers) t.join();
		for(Thread t: producers) t.join();
		
	}
	
}
