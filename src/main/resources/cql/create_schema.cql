CREATE TABLE if not exists object ( 
	key text,
	value text,
	PRIMARY KEY (key, value)
) with gc_grace_seconds = 10;
