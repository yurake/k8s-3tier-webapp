package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RedisServiceTest {

	@Test
	void testOnStartError() {
		RedisService svc = new RedisService();
		svc.subscribeRedistoMysql();
	}

	@Test
	void testIsActiveError() {
		RedisService svc = new RedisService();
		assertEquals(svc.ping(), false);
	}

}
