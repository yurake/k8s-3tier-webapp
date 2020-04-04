package webapp.tier.service;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testOnStartError() {
		ActiveMqService svc = new ActiveMqService();
		svc.run();
	}
}
