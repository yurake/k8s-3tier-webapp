package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import redis.clients.jedis.Jedis;

class RedisSubscribeServiceToMysqlTest {

	@Inject
	RedisSubscribeServiceToMysql svc;

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
			RedisSubscribeServiceToMysql svc = new RedisSubscribeServiceToMysql();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSubscribe() {
		try {
			RedisSubscribeServiceToMysql rsvc = new RedisSubscribeServiceToMysql() {
				public Jedis createJedis() {
					Jedis jedis = createJedisMock();
					return jedis;
				}
			};

			Thread thread = new Thread(rsvc);
			thread.start();
			thread.join(3000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testsubscribeRedisNoError() {
		try {
			Jedis jedis = mock(Jedis.class);
			RedisSubscribeServiceToMysql rsvc = new RedisSubscribeServiceToMysql() {
				public Jedis createJedis() {
					return jedis;
				}
			};

			Thread thread = new Thread(rsvc);
			thread.start();
			thread.join(3000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
