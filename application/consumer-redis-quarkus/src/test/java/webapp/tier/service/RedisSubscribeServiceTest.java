package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.fppt.jedismock.RedisServer;

import io.quarkus.test.junit.QuarkusTest;
import redis.clients.jedis.Jedis;

@QuarkusTest
class RedisSubscribeServiceTest {

	@Inject
	RedisSubscribeService svc;

	private static RedisServer server = null;
	
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
	void testSubscribe() {
		try {
			RedisSubscribeService svc = new RedisSubscribeService();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
		@Test
		void testPingTrue() {
			RedisSubscribeService svc = new RedisSubscribeService() {
				public Jedis createJedis() {
					Jedis jedis = createJedisMock();
					return jedis;
				}
			};
			assertThat(svc.ping(), is(true));
		}

		@Test
		void testPingFalse() {
			RedisSubscribeService svc = new RedisSubscribeService() {
				public Jedis createJedis() {
					Jedis jedis = Mockito.mock(Jedis.class);
					when(jedis.ping()).thenReturn("NG");
					return jedis;
				}
			};
			assertThat(svc.ping(), is(false));
		}

		@Test
		void testPingFalseException() {
			RedisSubscribeService svc = new RedisSubscribeService();
			assertThat(svc.ping(), is(false));
		}

}
