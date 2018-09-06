create keyspace if not exists datastax_global_store WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

use datastax_global_store; 

CREATE TABLE if not exists object ( 
	key text,
	value text,
	PRIMARY KEY (key, value)
) with gc_grace_seconds = 10;

