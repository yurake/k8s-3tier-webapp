package webapp.tier.cache.hazelcast;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.HazelcastInstance;

class ConnectHazelcastTest {

	@Test
	void testGetInstanceError() throws IOException, IllegalStateException {
		HazelcastInstance mockInstance = null;
		try {
			mockInstance = ConnectHazelcast.getInstance();
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
