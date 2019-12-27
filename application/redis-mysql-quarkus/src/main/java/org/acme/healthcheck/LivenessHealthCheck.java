package org.acme.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.acme.service.RedisService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(LivenessHealthCheck.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		RedisService redissvc = new RedisService();
		if (redissvc.ping()) {
			LOG.info("Liveness: UP");
			return HealthCheckResponse.up("Cache Server connection health check");
		} else {
			LOG.warning("Liveness: DOWN");
			return HealthCheckResponse.down("Cache Server connection health check");
		}
	}

}
