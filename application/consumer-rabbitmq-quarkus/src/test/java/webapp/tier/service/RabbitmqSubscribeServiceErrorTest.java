package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqSubscribeServiceErrorTest {

	@Test
	void testSubscribeError() {
		try {
			RabbitmqSubscribeService svc = new RabbitmqSubscribeService();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
