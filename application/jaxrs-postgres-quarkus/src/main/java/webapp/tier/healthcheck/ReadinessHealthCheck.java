package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.PostgresService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheck.class.getSimpleName());

	@Inject
	PostgresService posgressvc;

	@Override
	public HealthCheckResponse call() {
		if (posgressvc.connectionStatus()) {
			LOG.fine("Readiness: UP");
			return HealthCheckResponse.up("Database connection health check");
		} else {
			LOG.warning("Readiness: DOWN");
			return HealthCheckResponse.down("Database connection health check");
		}
	}
}
