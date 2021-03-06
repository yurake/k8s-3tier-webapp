package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RabbitmqService;

@QuarkusTest
class ReadinessHealthCheckRabbitmqTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckRabbitmq hc = new ReadinessHealthCheckRabbitmq();
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckRabbitmq hc = new ReadinessHealthCheckRabbitmq() {
			protected RabbitmqService createRabbitmqService() {
				RabbitmqService mock = Mockito.mock(RabbitmqService.class);
				Mockito.when(mock.isActive()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
