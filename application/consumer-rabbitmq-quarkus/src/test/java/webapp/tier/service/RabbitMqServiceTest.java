package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitMqServiceTest {

	@Test
	void testOnStartError() {
		RabbitMqService svc = new RabbitMqService();
		svc.run();
	}

	@Test
	void testIsActiveError() {
		RabbitMqService svc = new RabbitMqService();
		assertEquals(svc.isActive(), false);
	}

}
