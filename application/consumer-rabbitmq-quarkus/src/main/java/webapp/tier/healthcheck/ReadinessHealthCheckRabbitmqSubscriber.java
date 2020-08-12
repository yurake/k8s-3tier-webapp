package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RabbitmqSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRabbitmqSubscriber extends ReadinessHealthCheckRabbitmq {

	@Override
	public HealthCheckResponse call() {
		RabbitmqSubscribeService svc = this.createRabbitmqSubscribeService();
		return checkRabbitmqService(svc);
	}

	protected RabbitmqSubscribeService createRabbitmqSubscribeService() {
		return new RabbitmqSubscribeService();
	}

}
