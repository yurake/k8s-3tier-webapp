DROP KEYSPACE IF EXISTS test;
CREATE KEYSPACE IF NOT EXISTS webapp WITH replication = {
  'class':'SimpleStrategy',
  'replication_factor':1
};
CREATE TABLE IF NOT EXISTS webapp.msg(
  id int,
  msg text,
  PRIMARY KEY(id)
);
