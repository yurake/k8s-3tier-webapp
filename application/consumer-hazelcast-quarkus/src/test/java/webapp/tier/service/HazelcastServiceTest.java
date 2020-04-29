package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastServiceTest {

	@Test
	void testOnStartError() {
		HazelcastService svc = new HazelcastService();
		try {
			svc.run();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testIsActiveError() {
		HazelcastService svc = new HazelcastService();
		assertEquals(false, svc.isActive(), "Unexpected status");
	}

}
