create keyspace if not exists datastax_global_store WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1} with durable_writes=false;

use datastax_global_store; 

CREATE TABLE if not exists object ( 
	key text,
	value text,
	PRIMARY KEY (key, value)
) with gc_grace_seconds = 10;


CREATE KEYSPACE messages
  WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE messages.message (
 id timeuuid ,
 transactionId text ,
 legId int ,
 msgId text ,
 settlementDate text static ,
 settlementCycle int static ,
 amount int static ,
 creditorId text static ,
 debtorId text static ,
 instructingId text static ,
 instructedId text static ,
 creditorAgentId text static ,
 debtorAgentId text static ,
 rawMessage text ,
 PRIMARY KEY (transactionId, id, legId)
) WITH default_time_to_live = 300
  AND  gc_grace_seconds=30
  AND compaction = {'compaction_window_size': '1',  'compaction_window_unit': 'MINUTES', 'class': 'org.apache.cassandra.db.compaction.TimeWindowCompactionStrategy'};