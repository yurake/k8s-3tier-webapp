package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RedisSubscribeService;

@QuarkusTest
class ReadinessHealthCheckRedisSubscriberTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckRedisSubscriber hc = new ReadinessHealthCheckRedisSubscriber() {
			protected RedisSubscribeService createRedisSubscribeService() {
				RedisSubscribeService mock = Mockito.mock(RedisSubscribeService.class);
				Mockito.when(mock.ping()).thenReturn(false);
				return mock;
			}
		};
		assertEquals(Status.DOWN, hc.call().getStatus(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckRedisSubscriber hc = new ReadinessHealthCheckRedisSubscriber() {
			protected RedisSubscribeService createRedisSubscribeService() {
				RedisSubscribeService mock = Mockito.mock(RedisSubscribeService.class);
				Mockito.when(mock.ping()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}

}
