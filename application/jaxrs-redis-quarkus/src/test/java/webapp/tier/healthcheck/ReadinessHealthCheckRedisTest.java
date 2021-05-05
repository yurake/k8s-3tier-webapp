package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RedisService;

@QuarkusTest
class ReadinessHealthCheckRedisTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckRedis hc = new ReadinessHealthCheckRedis() {
			protected RedisService createRedisService() {
				RedisService mock = Mockito.mock(RedisService.class);
				Mockito.when(mock.ping()).thenReturn(false);
				return mock;
			}
		};
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckRedis hc = new ReadinessHealthCheckRedis() {
			protected RedisService createRedisService() {
				RedisService mock = Mockito.mock(RedisService.class);
				Mockito.when(mock.ping()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
