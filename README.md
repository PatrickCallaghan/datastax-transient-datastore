# datastax-transient-datastore

The transient datastore example is a example of using DataStax Enterprise for storing data that doesn't live very long. In this example the data lives for only a couple of seconds but needs to be highly available, scalable and have very fast performance. 

To ensure low latency reads we need to ensure that we can remove data as quick as possible so in the table definition we set the gc_grace_seconds = 10 seconds to allow data to be removed from the underlying data files as they are flushed to disk and compacted.

This example writes data into a table and then puts a request on a queue to read the data. Once the data is read, we can then delete it. There is no requirement for the data to stick around any longer. We can check the size of the data on disk by checking the table directory in the cassandar data directory and ensuring that the file sizes never grow over a certain size.   

To create the schema, run the following in DataStax Studio

	CREATE TABLE if not exists object ( 
		key text,
		value text,
		PRIMARY KEY (key, value)
	) with gc_grace_seconds = 10;


To create some loyalty points and redeems, run the following with your credentials and connection details from Apollo 
	
	mvn exec:java -Dexec.mainClass="com.datastax.loyalty.Main"  -DcredsZip=/tmp/creds.zip -Dusername=Tester -Dpassword=pass -Dkeyspace=mykeyspace

	
