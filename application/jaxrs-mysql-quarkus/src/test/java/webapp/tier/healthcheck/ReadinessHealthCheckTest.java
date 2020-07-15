package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ReadinessHealthCheckTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheck hc = Mockito.mock(ReadinessHealthCheck.class);
		HealthCheckResponse hr = new HealthCheckResponse("test", State.DOWN, null);
		when(hc.call()).thenReturn(hr);
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheck hc = Mockito.mock(ReadinessHealthCheck.class);
		HealthCheckResponse hr = new HealthCheckResponse("test", State.UP, null);
		when(hc.call()).thenReturn(hr);
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
