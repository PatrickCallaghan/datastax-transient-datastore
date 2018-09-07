package com.datastax.datastore;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.datastore.dao.ObjectData;
import com.datastax.demo.utils.PropertyHelper;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	public Main() {

		BlockingQueue<ObjectData> writerQueue = new ArrayBlockingQueue<ObjectData>(100000);
		BlockingQueue<String> readerQueue = new ArrayBlockingQueue<String>(100000);
		
		//Executor for Threads
		int noOfThreads = Integer.parseInt(PropertyHelper.getProperty("noOfThreads", "4"));
		ExecutorService readerExecutor = Executors.newFixedThreadPool(noOfThreads*2	);		
		ExecutorService writerExecutor = Executors.newFixedThreadPool(noOfThreads);
		
		DataStoreService service = new DataStoreService();
		
		for (int i = 0; i < noOfThreads; i++){		
			writerExecutor.submit(new ObjectWriter(writerQueue, readerQueue, service));
		}

		for (int i = 0; i < noOfThreads*2; i++){
			readerExecutor.submit(new ObjectReader(readerQueue, service));		
		}

		while (true){
			
			ObjectData data = new ObjectData();
			data.setKey(UUID.randomUUID().toString());
			data.setValue(UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString());

			try {
				writerQueue.put(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}		
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();

		System.exit(0);
	}
}
