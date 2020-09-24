package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.PostgresService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckPostgres implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		PostgresService svc = this.createPostgresService();
		return checkPostgresService(svc);
	}

	protected HealthCheckResponse checkPostgresService(PostgresService svc) {
		String msg = "Database connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.connectionStatus(), msg);
	}

	protected PostgresService createPostgresService() {
		return new PostgresService();
	}
}
