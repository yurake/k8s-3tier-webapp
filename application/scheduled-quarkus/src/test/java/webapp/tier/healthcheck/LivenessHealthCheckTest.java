package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

class LivenessHealthCheckTest {

	@Test
	void testCallError() {
		LivenessHealthCheck lhc = new LivenessHealthCheck();
		assertEquals(lhc.call().getState(), HealthCheckResponse.State.DOWN);
	}

}
