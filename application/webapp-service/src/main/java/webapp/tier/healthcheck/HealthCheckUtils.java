package webapp.tier.healthcheck;

import java.util.logging.Logger;

import org.eclipse.microprofile.health.HealthCheckResponse;

public class HealthCheckUtils {

	private static final Logger LOG = Logger.getLogger(HealthCheckUtils.class.getSimpleName());

	public static HealthCheckResponse respHealthCheckStatus(boolean status, String msg) {
		if (status) {
			LOG.fine("Liveness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			LOG.warning("Liveness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}

}
