package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

class LivenessHealthCheckTest {

	@Test
	void test() {
		LivenessHealthCheck hc = new LivenessHealthCheck();
		assertThat(hc.call().getState(), is(State.UP));
		assertThat(hc.call().getName(), is("OK"));
	}
}
