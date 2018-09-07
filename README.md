# datastax-transient-datastore



To create the schema, run the following

	mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetup" -DcontactPoints=localhost
	
To create transient transactions, run the following
	
	mvn clean compile exec:java -Dexec.mainClass="com.datastax.datastore.Main"  -DcontactPoints=localhost

