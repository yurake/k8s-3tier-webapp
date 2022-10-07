package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LivenessHealthCheckRedisSubscriberTest {

	@Test
	void testCallError() {
		LivenessHealthCheckRedisSubscriber hc = new LivenessHealthCheckRedisSubscriber();
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}

}
