CREATE ROLE "postgresdba" NOLOGIN;
CREATE ROLE "webapp" PASSWORD 'webapp' LOGIN;
GRANT "postgresdba" TO "webapp";

CREATE DATABASE webapp OWNER "postgresdba" ENCODING 'UTF8';

\c webapp;

-- Created at postgres database
CREATE TABLE msg (
  id integer not null,
  msg varchar(255) not null,
  created_time timestamp not null default current_timestamp,
  updated_time timestamp not null default current_timestamp,
  primary key (id)
);

ALTER TABLE msg OWNER TO postgresdba;