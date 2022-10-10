package webapp.tier.util;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.HazelcastInstance;

class HazelcastInstanceConfiguratorTest {

//	@Test
//	void testGetInstance() {
//		HazelcastInstance mockInstance = null;
//		try {
//			mockInstance = Hazelcast.newHazelcastInstance();
//			HazelcastInstanceConfigurator.getInstance();
//		} catch (Exception expected) {
//			expected.fillInStackTrace();
//			fail();
//		} finally {
//			mockInstance.shutdown();
//		}
//	}

	@Test
	void testGetInstanceError() {
		HazelcastInstance mockInstance = null;
		try {
			mockInstance = HazelcastInstanceConfigurator.getInstance();
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
