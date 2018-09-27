package com.datastax.datastore;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.datastore.dao.MessageDAO;
import com.datastax.datastore.dao.MessageObject;

public class DataStoreMessageService {

	private Logger logger = LoggerFactory.getLogger(DataStoreMessageService.class);
	private MessageDAO dao = new MessageDAO();
	private static AtomicLong insertCounter = new AtomicLong(0);
	private static AtomicLong deleteCounter = new AtomicLong(0);
	
	public DataStoreMessageService(){}
	
	public void saveMessage(MessageObject data){
		try {
			dao.putObjectInStore(data);
			if (insertCounter.incrementAndGet() % 10000 == 0){
				logger.info("Wrote " + insertCounter.get() + " transactions"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMessage(MessageObject data){
		try {			
			return dao.getObjectFromStore(data.getTransactionId(), data.getId(), data.getLegId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
