package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import io.quarkus.test.junit.QuarkusTest;
import redis.clients.jedis.Jedis;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class RedisServiceTest {

	@Inject
	RedisService svc;

	private static RedisServer server = null;
	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

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

	static Jedis createJedisMock() {
		return new Jedis(server.getHost(), server.getBindPort());
	}

	private Jedis createJedisErrorMock() {
		return new Jedis(server.getHost(), 9999);
	}

	@Test
	void testPingTrue() {
		RedisService rsvc = new RedisService() {
			public Jedis createJedis() {
				Jedis jedis = createJedisMock();
				return jedis;
			}
		};
		assertThat(rsvc.ping(), is(true));
	}

	@Test
	void testPingFalseNull() {
		RedisService rsvc = new RedisService() {
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

	@Test
	void testGetMsg() throws NoSuchAlgorithmException, RuntimeException {
		Jedis jedis = createJedisMock();
		MsgBean expected = svc.putMsg(jedis);
		MsgBean msgbean = svc.getMsg(jedis);
		svc.flushAll(jedis);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
		assertThat(msgbean.getId(), is(expected.getId()));
	}

	@Test
	void testGetMsgError() {
		Jedis jedis = createJedisErrorMock();
		try {
			svc.getMsg(jedis);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testGetMsgList10() throws NoSuchAlgorithmException, RuntimeException {
		Jedis jedis = createJedisMock();

		List<Integer> expecteds = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			expecteds.add(svc.putMsg(jedis).getId());
		}

		List<MsgBean> msgbeans = svc.getMsgList(jedis);
		svc.flushAll(jedis);
		msgbeans.forEach(s -> {
			assertThat(s.getFullmsg(), containsString(respbody));
			assertThat(expecteds.contains(s.getId()), is(true));
		});
	}

	@Test
	void testGetMsgList0() throws NoSuchAlgorithmException, RuntimeException {
		Jedis jedis = createJedisMock();

		List<MsgBean> msgbeans = svc.getMsgList(jedis);
		svc.flushAll(jedis);
		msgbeans.forEach(s -> {
			assertThat(s.getId(), is(0));
			assertThat(s.getMessage(), is("No Data."));
		});
	}

	@Test
	void testGetMsgListError() {
		Jedis jedis = createJedisErrorMock();
		try {
			svc.getMsgList(jedis);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testPutMsg() throws NoSuchAlgorithmException, RuntimeException {
		Jedis jedis = createJedisMock();
		MsgBean msgbean = svc.putMsg(jedis);
		svc.flushAll(jedis);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
	}

	@Test
	void testPutMsgError() {
		Jedis jedis = createJedisErrorMock();
		try {
			svc.putMsg(jedis);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Put Error.", e.getMessage());
		}
	}

	@Test
	void testPublishMsg() throws NoSuchAlgorithmException, RuntimeException {
		Jedis jedis = createJedisMock();
		MsgBean msgbean = svc.publishMsg(jedis);
		svc.flushAll(jedis);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
	}

	@Test
	void testPublishMsgError() {
		Jedis jedis = createJedisErrorMock();
		try {
			svc.publishMsg(jedis);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
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

	@Test
	void testSubscribeError() {
		try {
			RedisService rsvc = new RedisService() {
				public Jedis createJedis() {
					Jedis jedis = createJedisMock();
					return jedis;
				}
			};
			MockErrorMsgPublisher th = new MockErrorMsgPublisher();
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
