package webapp.tier.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.util.GetConfig;

@ApplicationScoped
public class RedisSubscriberToMysql extends JedisPubSub {

	@Inject
	MysqlInsertService mysqlsvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String splitkey = GetConfig.getResourceBundle("redis.splitkey");

	@Override
	public void onMessage(String channel, String message) {
		String fullmsg = null;
		String[] body = message.split(splitkey, 0);
		fullmsg = "Received channel:" + channel + ", id: " + body[0] + ", msg: "
				+ body[1];
		logger.info(fullmsg);
		try {
			mysqlsvc.insert(body);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Mysql Insert Error: ", e);
		}
	}
}
