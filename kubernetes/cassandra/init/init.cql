DROP KEYSPACE IF EXISTS test;
CREATE KEYSPACE IF NOT EXISTS webapp WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
CREATE TABLE IF NOT EXISTS webapp.fruit(store_id text, name text, description text, PRIMARY KEY((store_id), name));
