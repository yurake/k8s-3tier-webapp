package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.HazelcastSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheck.class.getSimpleName());


	@Override
	public HealthCheckResponse call() {
		String msg = "Cache Server connection health check";

		HazelcastSubscribeService hassvc = createHazelcastSubscribeService();
		if (hassvc.isActive()) {
			LOG.fine("Liveness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			LOG.warning("Liveness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}

	protected HazelcastSubscribeService createHazelcastSubscribeService() {
		return new HazelcastSubscribeService();
	}
}
