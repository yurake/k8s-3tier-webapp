package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckTest {

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheck hc = new ReadinessHealthCheck();
		assertThat(hc.call().getState(), is(State.UP));
	}
}
