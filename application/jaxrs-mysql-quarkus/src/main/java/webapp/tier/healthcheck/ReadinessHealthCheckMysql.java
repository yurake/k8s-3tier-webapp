package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import webapp.tier.service.MysqlService;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckMysql implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		MysqlService svc = this.createMysqlService();
		return checkMysqlService(svc);
	}

	protected HealthCheckResponse checkMysqlService(MysqlService svc) {
		String msg = "Database connection health check";
		return HealthCheckUtils.respHealthCheckStatus(svc.connectionStatus(), msg);
	}

	protected MysqlService createMysqlService() {
		return new MysqlService();
	}
}
