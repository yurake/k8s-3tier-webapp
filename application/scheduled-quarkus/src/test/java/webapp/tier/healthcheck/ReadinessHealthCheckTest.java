package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckTest {

	@Test
	void testCallError() {
		ReadinessHealthCheck hc = new ReadinessHealthCheck();
		HealthCheckResponse resp = hc.call();
		assertThat(resp.getState(), is(State.UP));
		assertThat(resp.getName(), is("OK"));
	}

}
