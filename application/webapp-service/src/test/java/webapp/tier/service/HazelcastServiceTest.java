package webapp.tier.service;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastServiceTest {

	@Test
	void testOnStartError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.putMapHazelcast();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsActiveError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.getMapHazelcast();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
