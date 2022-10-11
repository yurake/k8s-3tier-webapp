package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RedisService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRedis implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		RedisService svc = this.createRedisService();
		return checkRabbitmqService(svc);
	}

	protected HealthCheckResponse checkRabbitmqService(RedisService svc) {
		String msg = "Redis Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.ping(), msg);
	}


	protected RedisService createRedisService() {
		return new RedisService();
	}
}
