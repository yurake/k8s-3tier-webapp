package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ConnectHazelcastTest {

	@Test
	void testCreateNodeInstanceError() {
		ConnectHazelcast cast = new ConnectHazelcast();
		cast.createNodeInstance();
	}

	@Test
	void testGetInstanceError() throws IOException {
		try {
		ConnectHazelcast.getInstance();
		fail();
		} catch (IllegalStateException expected) {
			assertEquals(expected.getMessage(), "Unable to connect to any cluster.");
		}
	}
}
