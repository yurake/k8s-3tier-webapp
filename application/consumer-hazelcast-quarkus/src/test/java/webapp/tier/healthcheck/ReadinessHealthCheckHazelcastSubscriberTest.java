package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckHazelcastSubscriberTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckHazelcastSubscriber hc = new ReadinessHealthCheckHazelcastSubscriber();
		assertEquals(Status.DOWN, hc.call().getStatus(), "Unexpected status");
	}

}
