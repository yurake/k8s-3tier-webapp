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
			svc.putQueueHazelcast();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}

	@Test
	void testGetQueueHazelcast() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			svc.getQueueHazelcast();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}

	@Test
	void testPublishHazelcast() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			svc.publishHazelcast();
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}

}
