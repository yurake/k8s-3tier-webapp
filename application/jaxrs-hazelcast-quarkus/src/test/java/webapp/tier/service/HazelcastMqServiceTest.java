package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastMqServiceTest {

	@Test
	void testPutQueueHazelcast() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			svc.putMsg();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals("Put Error.", expected.getMessage());
		}
	}

	@Test
	void testGetQueueHazelcast() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals("Get Error.", expected.getMessage());
		}
	}

	@Test
	void testPublishHazelcast() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			svc.publishMsg();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals("Publish Error.", expected.getMessage());
		}
	}

}
