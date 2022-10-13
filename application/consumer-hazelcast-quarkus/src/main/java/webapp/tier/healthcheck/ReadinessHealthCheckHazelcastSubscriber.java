package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.HazelcastSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckHazelcastSubscriber implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String msg = "Hazelcast Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(HazelcastSubscribeService.isActive(), msg);
	}
}
