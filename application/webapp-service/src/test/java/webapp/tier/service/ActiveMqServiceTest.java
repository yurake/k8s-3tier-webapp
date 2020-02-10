package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testGetQueueConnectionError() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.getQueueConnection();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Could not connect to broker URL: tcp://activemq:61616. Reason: java.net.UnknownHostException: activemq");
		}
	}

	@Test
	void testGetTopicConnectionError() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.getTopicConnection();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Could not connect to broker URL: tcp://activemq:61616. Reason: java.net.UnknownHostException: activemq");
		}
	}

	@Test
	void testPutActiveMq() {
		ActiveMqService svc = new ActiveMqService();
		try {
			String result = svc.putActiveMq();
			assertEquals(result, "Error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetActiveMq() {
		ActiveMqService svc = new ActiveMqService();
		try {
			String result = svc.getActiveMq();
			assertEquals(result, "Error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPublishActiveMq() {
		ActiveMqService svc = new ActiveMqService();
		try {
			String result = svc.publishActiveMq();
			assertEquals(result, "Error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
