package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

class ReadinessHealthCheckTest {

	@Test
	void test() {
		ReadinessHealthCheck hc = new ReadinessHealthCheck();
		assertThat(hc.call().getState(), is(State.UP));
		assertThat(hc.call().getName(), is("OK"));
	}

}
