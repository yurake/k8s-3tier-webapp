package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastMqServiceTest {

	@Inject
	HazelcastMqService svc;

	private static HazelcastInstance mockInstance;

	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}

	@Test
	void testPutQueueHazelcast() {
		try {
			svc.putMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testPutQueueHazelcastErrorNull() {
		try {
			HazelcastInstance mockInstanceError = null;
			svc.putMsg(mockInstanceError);
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
		}
	}

	@Test
	void testGetQueueHazelcast() {
		try {
			svc.getMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testPublishHazelcast() {
		try {
			svc.publishMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

}
