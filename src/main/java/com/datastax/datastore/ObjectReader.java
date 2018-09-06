package com.datastax.datastore;

import java.util.concurrent.BlockingQueue;

class ObjectReader implements Runnable {

	private volatile boolean shutdown = false;
	private BlockingQueue<String> queue;
	private DataStoreService service;

	public ObjectReader(BlockingQueue<String> queue, DataStoreService service) {
		this.queue = queue;
		this.service = service;
	}

	@Override
	public void run() {
		String key;
		while (!shutdown) {
			key = queue.poll();
			
			if (key != null){
				service.getData(key);				
				service.deleteData(key);
			}				
		}
	}
}