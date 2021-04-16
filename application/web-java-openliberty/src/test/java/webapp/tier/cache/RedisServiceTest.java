package webapp.tier.cache;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import redis.clients.jedis.Jedis;

@Testcontainers
public class RedisServiceTest {

	private static Jedis jedis = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@SuppressWarnings("rawtypes")
	@Container
	public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
			.withExposedPorts(6379);

	@BeforeEach
	public void setUp() throws Exception {
		logger.info("start server");
		jedis = new Jedis(redis.getHost(), redis.getFirstMappedPort());
	}

	RedisService createRedisServiceMock() {
		return new RedisService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisMock();
				return jedis;
			}
		};
	}

	RedisService createRedisServiceErrorMock() {
		return new RedisService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisErrorMock();
				return jedis;
			}
		};
	}

	Jedis createJedisMock() {
		return jedis;
	}

	Jedis createJedisErrorMock() {
		return null;
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
		logger.info("start testpingTrue");
		RedisService svc = createRedisServiceMock();
		try {
			assertThat(svc.ping(), is(true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		logger.info("end testpingTrue");
	}

	@Test
	void testpingFalse() {
		logger.info("start testpingFalse");
		RedisService svc = createRedisServiceErrorMock();
		try {
			assertThat(svc.ping(), is(false));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		logger.info("end testpingTrue");
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
