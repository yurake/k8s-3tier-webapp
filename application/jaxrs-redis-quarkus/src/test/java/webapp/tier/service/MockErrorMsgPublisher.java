package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;

public class MockErrorMsgPublisher extends Thread {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Jedis jedis = RedisServiceTest.createJedisMock();
		try {
			Thread.sleep(1000);
			jedis.publish("pubsub", "ErrorMsg");
			jedis.expire("1111", 3600);
			logger.log(Level.INFO, "Test Publish");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Test Error", e);
		} finally {
			jedis.close();
		}
	}
}