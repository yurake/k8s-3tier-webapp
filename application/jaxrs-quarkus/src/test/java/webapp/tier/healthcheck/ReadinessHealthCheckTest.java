package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

class ReadinessHealthCheckTest {

	@Test
	void testCallError() {
		ReadinessHealthCheck lhc = new ReadinessHealthCheck();
		assertEquals(lhc.call().getState(), HealthCheckResponse.State.DOWN);
	}

}
