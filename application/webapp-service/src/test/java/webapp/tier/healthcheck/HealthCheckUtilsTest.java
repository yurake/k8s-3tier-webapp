package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HealthCheckUtilsTest {

	@Test
	void respHealthCheckStatusDown() {
		assertThat(HealthCheckUtils.respHealthCheckStatus(false, "Test").getStatus(),
				is(Status.DOWN));
	}

	@Test
	void respHealthCheckStatusUp() {
		assertThat(HealthCheckUtils.respHealthCheckStatus(true, "Test").getStatus(),
				is(Status.UP));
	}
}
