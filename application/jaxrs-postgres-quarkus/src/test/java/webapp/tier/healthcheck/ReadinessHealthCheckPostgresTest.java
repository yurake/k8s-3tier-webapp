package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.PostgresService;

@QuarkusTest
class ReadinessHealthCheckPostgresTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckPostgres hc = new ReadinessHealthCheckPostgres();
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckPostgres hc = new ReadinessHealthCheckPostgres() {
			protected PostgresService createPostgresService() {
				PostgresService mock = Mockito.mock(PostgresService.class);
				Mockito.when(mock.connectionStatus()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
