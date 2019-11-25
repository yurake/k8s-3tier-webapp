package org.acme.service;

import java.sql.Connection;

import javax.ws.rs.ext.Provider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Provider
public class RedisService {

	Connection con = null;

	public void subscribeRedis() {

		String servername = "redis";
		int serverport = 6379;
		String channel = "pubsub";
		String splitkey = ",";
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.subscribe(new JedisPubSub() {
				@Override
				public void onMessage(String channel, String message) {
					String fullmsg = null;
					String[] body = message.split(splitkey, 0);
					fullmsg = "Received channel:" + channel + ", id: " + body[0]+ ", msg: " + body[1];
					System.out.println(fullmsg);

					MysqlService mysqlsvc = new MysqlService();
					mysqlsvc.insertMysql(body);
				}
			}, channel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}
}
