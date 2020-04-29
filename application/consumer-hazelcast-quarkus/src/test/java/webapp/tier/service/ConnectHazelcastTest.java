package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ConnectHazelcastTest {

	@Test
	void testCreateNodeInstanceError() {
		ConnectHazelcast cast = new ConnectHazelcast();
		HazelcastInstance instance = cast.createNodeInstance();
		assertThat(instance, is(notNullValue()));
	}

	@Test
	void testGetInstanceError() throws IOException {
		try {
		ConnectHazelcast.getInstance();
		fail();
		} catch (IllegalStateException expected) {
			assertEquals("Unable to connect to any cluster.", expected.getMessage(), "Unexpected Error");
		}
	}
}
