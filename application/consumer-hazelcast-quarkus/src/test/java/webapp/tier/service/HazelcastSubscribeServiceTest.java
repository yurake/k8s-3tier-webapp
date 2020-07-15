package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastSubscribeServiceTest {

	@Inject
	HazelcastSubscribeService svc;

	@Test
	void testOnStartError() {
		try {
			svc.run();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testIsActiveError() {
		assertEquals(false, svc.isActive(), "Unexpected status");
	}

}
