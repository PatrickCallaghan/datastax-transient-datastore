package com.datastax.datastore;



import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.datastore.dao.MessageObject;

class MessageWriter implements Runnable {

	private static Logger log = LoggerFactory.getLogger(MessageWriter.class);
	
	private volatile boolean shutdown = false;
	private BlockingQueue<MessageObject> writerQueue;
	private BlockingQueue<MessageObject> readerQueue;
	private DataStoreMessageService service;
	

	public MessageWriter(BlockingQueue<MessageObject> writerQueue, BlockingQueue<MessageObject> readerQueue, DataStoreMessageService service) {
		this.writerQueue = writerQueue;
		this.readerQueue = readerQueue;
		this.service = service;
	}

	@Override
	public void run() {
		MessageObject messageObject;
		while (!shutdown) {
			messageObject = writerQueue.poll();
			
			if (messageObject != null){
				service.saveMessage(messageObject);
				
				try {
					readerQueue.put(messageObject);
				} catch (Exception e) {
					log.error("Error writing read queue : " + e.getMessage());
				}
			}				
		}
	}
}