package webapp.controller;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

class WlpWebJavaSpringApplicationTest {

	@Test
	void testMain() {
		HazelcastInstance mockInstance = Hazelcast.newHazelcastInstance();
		WlpWebJavaSpringApplication.main(new String[] {});
		mockInstance.shutdown();
	}

}
