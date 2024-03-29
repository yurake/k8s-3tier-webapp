package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.HazelcastServiceStatus;

@QuarkusTest
class ReadinessHealthCheckHazelcastTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckHazelcast hc = new ReadinessHealthCheckHazelcast();
		assertThat(hc.call().getStatus(), is(Status.DOWN));
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckHazelcast hc = new ReadinessHealthCheckHazelcast() {
			@Override
			protected HazelcastServiceStatus createHazelcastStatus() {
				HazelcastServiceStatus mock = mock(HazelcastServiceStatus.class);
				when(mock.isActive()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}
}
