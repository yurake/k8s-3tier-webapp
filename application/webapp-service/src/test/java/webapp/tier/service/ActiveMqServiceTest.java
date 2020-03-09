package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testPutMsg() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.putMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Put Error.", e.getMessage());
		}
	}

	@Test
	void testGetMsg() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testPublishMsg() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.publishMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}

}
