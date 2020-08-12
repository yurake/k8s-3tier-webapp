package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthCheckRabbitmq implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("OK");
	}
}
