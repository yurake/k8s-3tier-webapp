package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

class ReadinessHealthCheckTest {

	@Test
	void testCallError() {
		ReadinessHealthCheck hc = new ReadinessHealthCheck();
		HealthCheckResponse resp = hc.call();
		assertEquals(resp.getState(), HealthCheckResponse.State.UP);
		assertEquals(resp.getName(), "OK");
	}

}
