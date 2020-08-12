package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckHazelcastTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckHazelcast hc = new ReadinessHealthCheckHazelcast();
		assertThat(hc.call().getState(), is(State.DOWN));
	}
}
