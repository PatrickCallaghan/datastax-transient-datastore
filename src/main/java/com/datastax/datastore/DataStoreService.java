package com.datastax.datastore;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.datastore.dao.GlobalStoreDAO;
import com.datastax.datastore.dao.ObjectData;

public class DataStoreService {

	private Logger logger = LoggerFactory.getLogger(DataStoreService.class);
	private GlobalStoreDAO dao = new GlobalStoreDAO();
	private static AtomicLong insertCounter = new AtomicLong(0);
	private static AtomicLong deleteCounter = new AtomicLong(0);
	public DataStoreService(){}
	
	public void saveData(ObjectData data){
		try {
			dao.putObjectInStore(data.getKey(), data.getValue());
			
			if (insertCounter.incrementAndGet() % 10000 == 0){
				logger.info("Wrote " + insertCounter.get() + " transactions"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObjectData getData(String key){
		try {			
			return dao.getObjectFromStore(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteData(String key) {
		try {			
			dao.delete(key);
			if (deleteCounter.incrementAndGet() % 10000 == 0){
				logger.info("Deleted " + deleteCounter.get() + " transactions"); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
}
