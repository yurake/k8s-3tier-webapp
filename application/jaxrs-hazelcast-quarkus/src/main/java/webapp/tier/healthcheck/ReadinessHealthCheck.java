package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.HazelcastCacheService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheck.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		String msg = "Cache Server connection health check";
		HazelcastCacheService hassvc = this.createHazelcastCacheService();
		if (hassvc.isActive()) {
			LOG.fine("Readiness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			LOG.warning("Readiness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}

	protected HazelcastCacheService createHazelcastCacheService() {
		return new HazelcastCacheService();
	}
}
