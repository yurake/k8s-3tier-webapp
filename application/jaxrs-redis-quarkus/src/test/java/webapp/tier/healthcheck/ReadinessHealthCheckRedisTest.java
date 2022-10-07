package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.Status;
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
		assertEquals(Status.DOWN, hc.call().getStatus(), "Unexpected status");
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
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");
	}
}
