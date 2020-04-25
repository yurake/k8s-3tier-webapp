package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LivenessHealthCheckTest {

	@Test
	void testCallError() {
		LivenessHealthCheck hc = new LivenessHealthCheck();
		assertEquals(hc.call().getState(), State.DOWN);
	}

}
