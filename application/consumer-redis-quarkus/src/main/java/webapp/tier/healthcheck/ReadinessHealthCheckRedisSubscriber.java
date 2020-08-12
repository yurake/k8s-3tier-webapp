package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RedisSubscribeService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRedisSubscriber extends ReadinessHealthCheckRedis {

	@Override
	public HealthCheckResponse call() {
		RedisSubscribeService svc = this.createRedisSubscribeService();
		return checkRabbitmqService(svc);
	}

	protected RedisSubscribeService createRedisSubscribeService() {
		return new RedisSubscribeService();
	}

}
