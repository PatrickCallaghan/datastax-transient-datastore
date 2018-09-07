package com.datastax.datastore;

import java.util.concurrent.BlockingQueue;

import com.datastax.datastore.dao.ObjectData;

class ObjectWriter implements Runnable {

	private volatile boolean shutdown = false;
	private BlockingQueue<ObjectData> writerQueue;
	private BlockingQueue<String> readerQueue;
	private DataStoreService service;
	

	public ObjectWriter(BlockingQueue<ObjectData> writerQueue, BlockingQueue<String> readerQueue, DataStoreService service) {
		this.writerQueue = writerQueue;
		this.readerQueue = readerQueue;
		this.service = service;
	}

	@Override
	public void run() {
		ObjectData data;
		while (!shutdown) {
			data = writerQueue.poll();
			
			if (data != null){
				service.saveData(data);
				
				try {
					readerQueue.put(data.getKey());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		}
	}
}