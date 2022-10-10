package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RabbitmqSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRabbitmqSubscriber implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		RabbitmqSubscribeService svc = this.createRabbitmqSubscribeService();
		return checkRabbitmqSubscribeService(svc);
	}

	protected HealthCheckResponse checkRabbitmqSubscribeService(RabbitmqSubscribeService svc) {
		String msg = "Rabbitmq Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.isActive(), msg);
	}

	protected RabbitmqSubscribeService createRabbitmqSubscribeService() {
		return new RabbitmqSubscribeService();
	}
}
