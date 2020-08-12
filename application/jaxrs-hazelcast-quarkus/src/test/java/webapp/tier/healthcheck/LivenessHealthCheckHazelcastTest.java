package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LivenessHealthCheckHazelcastTest {

	@Test
	void testCallError() {
		LivenessHealthCheckHazelcast hc = new LivenessHealthCheckHazelcast();
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}

}
