package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastServiceTest {

	@Test
	void testOnStartError() {
		HazelcastService svc = new HazelcastService();
		svc.run();
	}

	@Test
	void testIsActiveError() {
		HazelcastService svc = new HazelcastService();
		assertEquals(svc.isActive(), false);
	}

}
