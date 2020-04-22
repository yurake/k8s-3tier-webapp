package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import webapp.tier.service.PostgresService;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

	@Inject
	PostgresService posgresvc;

	private static final Logger LOG = Logger.getLogger(LivenessHealthCheck.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		if (posgresvc.connectionStatus()) {
			LOG.fine("LivenessHealthCheck: UP");
			return HealthCheckResponse.up("Database connection health check");
		} else {
			LOG.warning("LivenessHealthCheck: DOWN");
			return HealthCheckResponse.down("Database connection health check");
		}
	}
}
