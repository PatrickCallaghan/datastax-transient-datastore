package com.datastax.datastore.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.dse.driver.api.core.DseSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;


/** 
 * Global Cassandra DAO 
 * 
 * Provide insert/select statement for Object stores.
 * @author patrickcallaghan
 *
 */
public class GlobalStoreDAO {

	private Logger logger = LoggerFactory.getLogger(GlobalStoreDAO.class);

	private DseSession session;

	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static final String defaultKeyspace = "testing";
	private static final String storeObjectTableName = defaultKeyspace + ".object";
		
	private static final String getFromStoreCQL = "select key, value from " + storeObjectTableName + " where key = ?";
	private static final String putInStoreCQL = "insert into " + storeObjectTableName + " (key, value) values (?,?)";
	private static final String deleteFromStoreCQL = "delete from " + storeObjectTableName + " where key = ?";
	
	private PreparedStatement putInStore;
	private PreparedStatement getFromStore;
	private PreparedStatement deleteFromStore;
	
	public GlobalStoreDAO() {	
		session = DseSession.builder().withCloudSecureConnectBundle("/Users/patrickcallaghan/secure-connect-testing.zip")
		           .withAuthCredentials("Patrick","XxxxxxxxX")
		           .withKeyspace("testing")
		           .build();
		
		this.getFromStore = session.prepare(getFromStoreCQL);
		this.putInStore = session.prepare(putInStoreCQL);
		this.deleteFromStore = session.prepare(deleteFromStoreCQL);
	}
	
	public ObjectData getObjectFromStore(String key) throws Exception{
		
		BoundStatement bound = this.getFromStore.bind(key);
		
		ResultSet rs = session.execute(bound);
		
		
		if (rs != null && !rs.isFullyFetched()){
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
