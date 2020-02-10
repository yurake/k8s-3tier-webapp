package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastCacheServiceTest {

	@Test
	void testPutMapHazelcastError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.putMapHazelcast();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}

	@Test
	void testGetMapHazelcastError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.getMapHazelcast();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}

}
