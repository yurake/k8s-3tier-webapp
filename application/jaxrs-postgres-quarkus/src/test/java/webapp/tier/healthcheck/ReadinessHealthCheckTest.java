package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.PostgresService;

@QuarkusTest
class ReadinessHealthCheckTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheck hc = new ReadinessHealthCheck() {
			protected PostgresService createPostgresService() {
				PostgresService mock = Mockito.mock(PostgresService.class);
				Mockito.when(mock.connectionStatus()).thenReturn(false);
				return mock;
			}
		};
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheck hc = new ReadinessHealthCheck() {
			protected PostgresService createPostgresService() {
				PostgresService mock = Mockito.mock(PostgresService.class);
				Mockito.when(mock.connectionStatus()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
