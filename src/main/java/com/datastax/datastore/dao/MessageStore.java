package com.datastax.datastore.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

/** 
 * Global Cassandra DAO 
 * 
 * Provide insert/select statement for Object stores.
 * @author patrickcallaghan
 *
 */
public class MessageStore {

	private Logger logger = LoggerFactory.getLogger(MessageStore.class);

	private Cluster cluster;
	private Session session;

	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static final String defaultKeyspace = "datastax_global_store";
	private static final String storeObjectTableName = defaultKeyspace + ".object";
		
	private static final String getFromStoreCQL = "select key, value from " + storeObjectTableName + " where key = ?";
	private static final String putInStoreCQL = "insert into " + storeObjectTableName + " (key, value) values (?,?)";
	private static final String deleteFromStoreCQL = "delete from " + storeObjectTableName + " where key = ?";
	
	private PreparedStatement putInStore;
	private PreparedStatement getFromStore;
	private PreparedStatement deleteFromStore;
	
	public MessageStore() {
		String contactPoints = PropertyHelper.getProperty("contactPoints", "127.0.0.1");
		
		cluster = Cluster.builder().addContactPoints(contactPoints).build();
		session = cluster.connect();
		
		this.getFromStore = session.prepare(getFromStoreCQL);
		this.putInStore = session.prepare(putInStoreCQL);
		this.deleteFromStore = session.prepare(deleteFromStoreCQL);
	}
	
	public ObjectData getObjectFromStore(String key) throws Exception{
		
		BoundStatement bound = this.getFromStore.bind(key);
		
		ResultSet rs = session.execute(bound);
		if (rs != null && !rs.isExhausted()){
			return new ObjectData(key, rs.one().getString("value"));
		}else{
			return new ObjectData();
		}
	}
	
	public void putObjectInStore(String key, String value) throws IOException{
		
		BoundStatement bound = this.putInStore.bind(key, value);		
		session.execute(bound);
	}

	public void delete(String key) {
				
		BoundStatement bound = this.deleteFromStore.bind(key);		
		session.execute(bound);
	}
}
