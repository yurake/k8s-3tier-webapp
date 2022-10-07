package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RabbitmqSubscribeService;

@QuarkusTest
class ReadinessHealthCheckRabbitmqSubscriberTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckRabbitmqSubscriber hc = new ReadinessHealthCheckRabbitmqSubscriber();
		assertEquals(Status.DOWN, hc.call().getStatus(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckRabbitmqSubscriber hc = new ReadinessHealthCheckRabbitmqSubscriber() {
			protected RabbitmqSubscribeService createRabbitmqSubscribeService() {
				RabbitmqSubscribeService mock = Mockito.mock(RabbitmqSubscribeService.class);
				Mockito.when(mock.isActive()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}
}
