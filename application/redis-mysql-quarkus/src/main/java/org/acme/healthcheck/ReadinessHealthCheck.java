package org.acme.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheck.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		LOG.info("Readiness: UP");
		return HealthCheckResponse.up("Database connection health check");
	}

}
