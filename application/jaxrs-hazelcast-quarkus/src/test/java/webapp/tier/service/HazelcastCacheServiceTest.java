package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastCacheServiceTest{

	@Test
	void testPutMapHazelcastError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.setMsg();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals("Set Error.", expected.getMessage());
		}
	}

	@Test
	void testGetMapHazelcastError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals("Get Error.", expected.getMessage());
		}
	}

	@Test
	void testIsActiveError() {
		HazelcastCacheService svc = new HazelcastCacheService();
		assertEquals(false, svc.isActive());
	}

}
