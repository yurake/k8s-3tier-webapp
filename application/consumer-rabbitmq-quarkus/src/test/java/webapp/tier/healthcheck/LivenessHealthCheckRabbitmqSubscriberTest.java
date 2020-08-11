package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LivenessHealthCheckRabbitmqSubscriberTest {

	@Test
	void testCallUP() {
		LivenessHealthCheckRabbitmqSubscriber hc = new LivenessHealthCheckRabbitmqSubscriber();
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}

}
