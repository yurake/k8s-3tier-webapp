package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

class LivenessHealthCheckTest {

	@Test
	void testCallError() {
		LivenessHealthCheck hc = new LivenessHealthCheck();
		HealthCheckResponse resp = hc.call();
		assertEquals(resp.getState(), HealthCheckResponse.State.UP);
		assertEquals(resp.getName(), "OK");
	}

}
