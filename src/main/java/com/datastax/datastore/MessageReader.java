package com.datastax.datastore;



import java.util.concurrent.BlockingQueue;

import com.datastax.datastore.dao.MessageObject;

class MessageReader implements Runnable {

	private volatile boolean shutdown = false;
	private BlockingQueue<MessageObject> queue;
	private DataStoreMessageService service;

	public MessageReader(BlockingQueue<MessageObject> queue, DataStoreMessageService service) {
		this.queue = queue;
		this.service = service;
	}

	@Override
	public void run() {
		MessageObject data;
		while (true) {
			data = queue.poll();
			
			if (data != null){
				service.getMessage(data);				
				service.deleteData(data);
			}				
		}
	}
}