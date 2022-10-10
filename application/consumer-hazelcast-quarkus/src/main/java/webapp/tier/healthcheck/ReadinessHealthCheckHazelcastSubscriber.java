package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.HazelcastServiceStatus;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckHazelcastSubscriber implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		HazelcastServiceStatus svc = this.createHazelcastStatus();
		return checkHazelcastService(svc);
	}

	protected HealthCheckResponse checkHazelcastService(HazelcastServiceStatus svc) {
		String msg = "Hazelcast Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.isActive(), msg);
	}

	protected HazelcastServiceStatus createHazelcastStatus() {
		return new HazelcastServiceStatus();
	}
}
