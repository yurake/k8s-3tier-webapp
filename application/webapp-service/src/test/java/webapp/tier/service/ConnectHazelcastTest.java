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
	void testGetInstanceError() throws IOException, IllegalStateException {
		try {
		ConnectHazelcast.getInstance();
		fail();
		} catch (Exception expected) {
			assertEquals("Unable to connect to any cluster.", expected.getMessage());
		}
	}
}
