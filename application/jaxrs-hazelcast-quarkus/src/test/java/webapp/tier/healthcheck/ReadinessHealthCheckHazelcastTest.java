package webapp.tier.healthcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.HazelcastServiceStatus;

@QuarkusTest
class ReadinessHealthCheckHazelcastTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckHazelcast hc = new ReadinessHealthCheckHazelcast();
		assertThat(hc.call().getState(), is(State.DOWN));
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckHazelcast hc = new ReadinessHealthCheckHazelcast() {
			protected HazelcastServiceStatus createHazelcastStatus() {
				HazelcastServiceStatus mock = Mockito.mock(HazelcastServiceStatus.class);
				Mockito.when(mock.isActive()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
