package webapp.tier.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import io.quarkus.test.junit.QuarkusTest;
import redis.clients.jedis.Jedis;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
class RedisDeliverSubscriberTest {

	@Inject
	RedisSubscribeService svc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static RedisServer server = null;
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.split.key", String.class);
	private static int setexpire = ConfigProvider.getConfig().getValue("redis.set.expire", Integer.class);

	@BeforeEach
	public void setup() throws IOException {
		server = RedisServer.newRedisServer();
		server.start();
	}

	@AfterEach
	public void after() {
		server.stop();
		server = null;
	}

	protected static Jedis createJedisMock() {
		return new Jedis(server.getHost(), server.getBindPort());
	}

	@Test
	void testOnMessage() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);
		Jedis jedis = createJedisMock();
		jedis.publish(channel, body);
		jedis.expire(MsgUtils.intToString(msgbean.getId()), setexpire);
		jedis.close();
	}

}
