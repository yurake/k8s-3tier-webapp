package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testOnStartError() {
		ActiveMqService svc = new ActiveMqService();
		svc.run();
	}

	@Test
	void testIsActiveError() {
		ActiveMqService svc = new ActiveMqService();
		assertEquals(svc.isActive(), false);
	}

}
