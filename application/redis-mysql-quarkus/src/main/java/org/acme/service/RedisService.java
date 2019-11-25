package org.acme.service;

import java.sql.Connection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@ApplicationScoped
public class RedisService {

    @Inject
    Config config;

	Connection con = null;
	private String servername = config.getValue("redis.server.name", String.class);
	private int serverport = Integer.parseInt(config.getValue("redis.server.port", String.class));
	private String channel = config.getValue("redis.channel.name", String.class);
	private String splitkey = config.getValue("redis.split.key", String.class);

	public void subscribeRedis() {
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
