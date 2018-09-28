
package com.datastax.datastore.dao;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.driver.mapping.MappingManager;

/** 
 * Global Cassandra DAO 
 * 
 * Provide insert/select statement for Object stores.
 * @author patrickcallaghan
 *
 */
public class MessageDAO {

	private Logger logger = LoggerFactory.getLogger(MessageDAO.class);

	private Cluster cluster;
	private Session session;

	private Mapper<MessageObject> mapper;
	
	public MessageDAO() {
		String contactPoints = PropertyHelper.getProperty("contactPoints", "127.0.0.1");
		
		cluster = Cluster.builder().addContactPoints(contactPoints).build();
		session = cluster.connect();

		this.mapper = new MappingManager(this.session).mapper(MessageObject.class);
		mapper.setDefaultSaveOptions(Option.consistencyLevel(ConsistencyLevel.LOCAL_QUORUM));
		mapper.setDefaultGetOptions(Option.consistencyLevel(ConsistencyLevel.LOCAL_QUORUM));
	}
	
	public String getObjectFromStore(String transactionId, UUID id, int legId) {
			
		MessageObject messageObject = mapper.get(transactionId, id, legId);
		return messageObject.getTransactionId();
	}
	
	public void putObjectInStore(MessageObject value) throws IOException{
		
		mapper.save(value);
	}

	public void delete(MessageObject data) {
		
		mapper.delete(data);
	}
}
