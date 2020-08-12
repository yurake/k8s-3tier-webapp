package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RabbitmqService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRabbitmq implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		RabbitmqService svc = this.createRabbitmqService();
		return checkRabbitmqService(svc);
	}

	protected HealthCheckResponse checkRabbitmqService(RabbitmqService svc) {
		String msg = "Rabbitmq Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.isActive(), msg);
	}

	protected RabbitmqService createRabbitmqService() {
		return new RabbitmqService();
	}
}
