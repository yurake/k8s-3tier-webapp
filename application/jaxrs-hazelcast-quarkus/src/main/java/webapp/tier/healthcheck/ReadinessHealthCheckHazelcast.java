package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.HazelcastServiceStatus;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckHazelcast implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return checkHazelcastService();
	}

	protected HealthCheckResponse checkHazelcastService() {
		String msg = "Hazelcast Server connection health check";
		HazelcastServiceStatus sts = new HazelcastServiceStatus();
		return HealthCheckUtils.respHealthCheckStatus(sts.isActive(), msg);
	}

	protected HazelcastServiceStatus createHazelcastService() {
		return new HazelcastServiceStatus();
	}
}
