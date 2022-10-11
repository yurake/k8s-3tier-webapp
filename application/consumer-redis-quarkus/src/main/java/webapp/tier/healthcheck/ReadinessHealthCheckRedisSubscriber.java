package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RedisSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRedisSubscriber implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		RedisSubscribeService svc = this.createRedisService();
		return checkRedisService(svc);
	}

	protected HealthCheckResponse checkRedisService(RedisSubscribeService svc) {
		String msg = "Redis Server connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.ping(), msg);
	}


	protected RedisSubscribeService createRedisService() {
		return new RedisSubscribeService();
	}

}
