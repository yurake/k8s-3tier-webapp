package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RedisSubscribeService;

@QuarkusTest
class ReadinessHealthCheckRedisSubscriberTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckRedisSubscriber hc = new ReadinessHealthCheckRedisSubscriber() {
			protected RedisSubscribeService createRedisService() {
				RedisSubscribeService mock = mock(RedisSubscribeService.class);
				when(mock.ping()).thenReturn(false);
				return mock;
			}
		};
		assertEquals(Status.DOWN, hc.call().getStatus(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckRedisSubscriber hc = new ReadinessHealthCheckRedisSubscriber() {
			protected RedisSubscribeService createRedisService() {
				RedisSubscribeService mock = mock(RedisSubscribeService.class);
				when(mock.ping()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}
	
	@Test
	void testCallDownPrimitive() {
		ReadinessHealthCheckRedisSubscriber sub = new ReadinessHealthCheckRedisSubscriber();
		assertEquals(Status.DOWN, sub.call().getStatus(), "Unexpected status");
	}

}
