package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastServiceTest {

	@Test
	void testGetInstance() throws IOException, IllegalStateException {
		HazelcastInstance mockInstance = null;
		try {
			mockInstance = Hazelcast.newHazelcastInstance();
			HazelcastService.getInstance();
		} catch (Exception expected) {
			expected.fillInStackTrace();
			fail();
		} finally {
			mockInstance.shutdown();
		}
	}

	@Test
	void testGetInstanceError() throws IOException, IllegalStateException {
		HazelcastInstance mockInstance = null;
		try {
			mockInstance = HazelcastService.getInstance();
			fail();
		} catch (Exception expected) {
			expected.fillInStackTrace();
		} finally {
			if (mockInstance != null) {
				mockInstance.shutdown();
			}
		}
	}
}
