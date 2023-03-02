package webapp.tier.healthcheck;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.health.HealthCheckResponse;

public final class HealthCheckUtils {
	
	private static final Logger logger = Logger
			.getLogger(HealthCheckUtils.class.getSimpleName());

	// Hide constructor
	private HealthCheckUtils() {
	}

	public static HealthCheckResponse respHealthCheckStatus(boolean status, String msg) {
		if (status) {
			logger.log(Level.FINE, "Liveness: UP");
			return HealthCheckResponse.up(msg);
		} else {
			logger.log(Level.WARNING, "Liveness: DOWN");
			return HealthCheckResponse.down(msg);
		}
	}

}
