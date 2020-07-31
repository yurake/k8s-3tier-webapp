package webapp.tier.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
	void testOnStartError() {
		try {
			RedisSubscribeService svc = new RedisSubscribeService();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testOnStart() {
		try {
			RedisSubscribeService rsvc = new RedisSubscribeService() {
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

	@Test
	void testPingTrue() {
		RedisSubscribeService rsvc = new RedisSubscribeService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisMock();
				return jedis;
			}
		};
		assertThat(rsvc.ping(), is(true));
	}

	@Test
	void testPingFalseNull() {
		RedisSubscribeService rsvc = new RedisSubscribeService() {
			public Jedis createJedis() {
				return null;
			}
		};
		assertThat(rsvc.ping(), is(false));
	}

	@Test
	void testPingFalse() {
		assertThat(svc.ping(), is(false));
	}
}
