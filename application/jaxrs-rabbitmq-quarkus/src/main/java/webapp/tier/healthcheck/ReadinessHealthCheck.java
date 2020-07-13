package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RabbitmqService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheck.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		String msg = "Database connection health check";

		RabbitmqService svc = this.createRabbitmqService();
		if (svc.isActive()) {
			LOG.fine("Readiness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			LOG.warning("Readiness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}

	protected RabbitmqService createRabbitmqService() {
		return new RabbitmqService();
	}
}
