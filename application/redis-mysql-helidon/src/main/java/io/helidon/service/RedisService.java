package io.helidon.service;

import java.sql.Connection;

import javax.enterprise.context.ApplicationScoped;

import io.helidon.util.GetConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@ApplicationScoped
public class RedisService {

	Connection con = null;

	private static String servername = GetConfig.getResourceBundle("redis.server");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.port"));
	private static String channel = GetConfig.getResourceBundle("redis.channel");
	private static String splitkey = GetConfig.getResourceBundle("redis.splitkey");

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
