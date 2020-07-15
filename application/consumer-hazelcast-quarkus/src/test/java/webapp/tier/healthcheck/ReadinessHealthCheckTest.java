package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheck hc = new ReadinessHealthCheck();
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

}
