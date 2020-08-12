package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LivenessHealthCheckRedisTest {

	@Test
	void testCallError() {
		LivenessHealthCheckRedis hc = new LivenessHealthCheckRedis();
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}

}
