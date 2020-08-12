package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RabbitmqService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRabbitmq implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheckRabbitmq.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		RabbitmqService svc = this.createRabbitmqService();
		return checkRabbitmqService(svc);
	}

	protected HealthCheckResponse checkRabbitmqService(RabbitmqService svc) {
		String msg = "Database connection health check";
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
