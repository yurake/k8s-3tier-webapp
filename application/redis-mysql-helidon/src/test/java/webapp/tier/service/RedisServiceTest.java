package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class RedisServiceTest {

	@Test
	void testSubscribeRedistoMysqlError() {
		RedisService svc = new RedisService();
		try {
			svc.subscribeRedistoMysql();
		} catch (Exception e) {
			fail();
		}
	}

}
