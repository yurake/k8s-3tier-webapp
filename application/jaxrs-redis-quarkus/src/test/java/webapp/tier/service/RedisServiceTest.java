package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RedisServiceTest {

	@Test
	void testPingError() {
		RedisService redissvc = new RedisService();
		assertEquals(false, redissvc.ping());
	}

	@Test
	void testGetMsgError() {
		RedisService svc = new RedisService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testGetMsgListError() {
		RedisService svc = new RedisService();
		try {
			svc.getMsgList();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testPutMsgError() {
		RedisService svc = new RedisService();
		try {
			svc.putMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Put Error.", e.getMessage());
		}
	}

	@Test
	void testPublishMsgError() {
		RedisService svc = new RedisService();
		try {
			svc.publishMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}
}
