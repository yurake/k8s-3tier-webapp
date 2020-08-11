package webapp.tier.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.RedisService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckRedisSubscriber implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(ReadinessHealthCheckRedisSubscriber.class.getSimpleName());

	@Override
	public HealthCheckResponse call() {
		String msg = "Cache Server connection health check";
		RedisService redissvc = this.createRedisService();
		if (redissvc.ping()) {
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
