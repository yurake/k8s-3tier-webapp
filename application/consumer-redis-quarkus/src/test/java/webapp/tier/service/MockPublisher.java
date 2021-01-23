package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;

public class MockPublisher extends Thread {

	private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public void run() {
		Jedis jedis = RedisSubscribeServiceTest.createJedisMock();
		try {
			Thread.sleep(1000);
			jedis.publish("pubsub", "1111,Test");
			jedis.expire("1111", 3600);
			LOG.log(Level.INFO, "Test Publish");

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Test Error", e);
		} finally {
			jedis.close();
		}
	}
}