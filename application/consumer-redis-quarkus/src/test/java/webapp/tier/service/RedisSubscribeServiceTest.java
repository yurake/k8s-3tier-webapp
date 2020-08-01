package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import io.quarkus.test.junit.QuarkusTest;
import redis.clients.jedis.Jedis;

@QuarkusTest
class RedisSubscribeServiceTest {

	@Inject
	RedisSubscribeService svc;

	private static RedisServer server = null;

	@BeforeAll
	public static void setup() throws IOException {
		server = RedisServer.newRedisServer();
		server.start();
	}

	@AfterAll
	public static void after() {
		server.stop();
		server = null;
	}

	protected static Jedis createJedisMock() {
		return new Jedis(server.getHost(), server.getBindPort());
	}

	@Test
	void testSubscribeError() {
		try {
			RedisSubscribeService svc = new RedisSubscribeService();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSubscribe() {
		try {
			RedisService rsvc = new RedisService() {
				public Jedis createJedis() {
					Jedis jedis = createJedisMock();
					return jedis;
				}
			};
			MockPublisher th = new MockPublisher();
			th.start();

			Thread thread = new Thread(rsvc);
			thread.start();
			thread.join(3000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
