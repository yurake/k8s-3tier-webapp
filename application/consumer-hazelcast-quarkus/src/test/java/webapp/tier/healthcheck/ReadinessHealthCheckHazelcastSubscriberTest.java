package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.hazelcast.HazelcastServerTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(HazelcastServerTestResource.class)
class ReadinessHealthCheckHazelcastSubscriberTest {

	@Test
	void testCallUp() {
		ReadinessHealthCheckHazelcastSubscriber hc = new ReadinessHealthCheckHazelcastSubscriber();
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}

}
