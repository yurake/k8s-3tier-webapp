package webapp.tier.cache;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import redis.clients.jedis.Jedis;

class RedisServiceTest {

	private static RedisServer server = null;
	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@BeforeEach
	public void setup() throws IOException {
		server = RedisServer.newRedisServer(6370);
		server.start();
		System.out.println("start server: " + server.getHost() + ", " + server.getBindPort());
	}

	@AfterEach
	public void after() {
		server.stop();
		System.out.println("stop server");
	}

	static RedisService createRedisServiceMock() {
		return new RedisService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisMock();
				return jedis;
			}
		};
	}

	static RedisService createRedisServiceErrorMock() {
		return new RedisService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisErrorMock();
				return jedis;
			}
		};
	}

	static Jedis createJedisMock() {
		return new Jedis(server.getHost(), server.getBindPort());
	}

	static Jedis createJedisErrorMock() {
		return new Jedis(server.getHost(), 9999);
	}

	@Test
	void testcreateJedis() {
		try {
			RedisService svc = new RedisService();
			assertThat(svc.createJedis(), is(instanceOf(Jedis.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail("まだ実装されていません");
		}
	}

	@Test
	void testpingTrue() {
		System.out.println("start testpingTrue");
		RedisService svc = createRedisServiceMock();
		try {
			assertThat(svc.ping(), is(true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("end testpingTrue");
	}

	@Test
	void testpingFalse() {
		System.out.println("start testpingFalse");
		RedisService svc = createRedisServiceErrorMock();
		try {
			assertThat(svc.ping(), is(false));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("end testpingTrue");
	}

	@Test
	void testset() {
		try {
			RedisService svc = createRedisServiceMock();
			String result = svc.set();
			assertThat(result, containsString("Set id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testgetNoData() {
		try {
			RedisService svc = createRedisServiceMock();
			String result = svc.get().get(0);
			assertThat(result, containsString("No Data"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testgetWithData() {
		try {
			RedisService svc = createRedisServiceMock();
			svc.set();
			String result = svc.get().get(0);
			assertThat(result, containsString("Selected Msg: id: "));
			assertThat(result, containsString(", message: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testpublish() {
		try {
			RedisService svc = createRedisServiceMock();
			String result = svc.publish();
			assertThat(result, containsString("Publish channel:pubsub, id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
