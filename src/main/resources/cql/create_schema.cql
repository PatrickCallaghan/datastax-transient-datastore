CREATE KEYSPACE messages
  WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

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
  AND  gc_grace_seconds=30;
