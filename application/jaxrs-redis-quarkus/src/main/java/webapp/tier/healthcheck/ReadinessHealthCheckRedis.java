package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RedisService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRedis implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheckRedis.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		RedisService svc = this.createRedisService();
		return checkRabbitmqService(svc);
	}

	protected HealthCheckResponse checkRabbitmqService(RedisService svc) {
		String msg = "Cache Server connection health check";
		if (svc.ping()) {
			LOG.fine("Liveness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			LOG.warning("Liveness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}


	protected RedisService createRedisService() {
		return new RedisService();
	}
}
