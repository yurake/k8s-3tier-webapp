package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MemcachedServiceTest {

	@Test
	void testSetMemcachedError() {
		MemcachedService svc = new MemcachedService();
			try {
				assertEquals("Set Error.", svc.setMsg());
			} catch (Exception e) {
				fail();
			}
	}

	@Test
	void testGetMemcachedError() {
		MemcachedService svc = new MemcachedService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception expected) {
			assertEquals("Get Error.", expected.getMessage());
		}
	}

}
