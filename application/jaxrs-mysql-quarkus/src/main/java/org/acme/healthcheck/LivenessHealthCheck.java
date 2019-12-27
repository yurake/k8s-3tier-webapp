package org.acme.healthcheck;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.acme.service.MysqlService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

	private static final Logger LOG = Logger.getLogger(LivenessHealthCheck.class.getSimpleName());

	MysqlService mysqlsvc = new MysqlService();

	@Override
	public HealthCheckResponse call() {
		MysqlService mysqlsvc = new MysqlService();

		if (mysqlsvc.connectionStatus()) {
			LOG.info("Liveness: UP");
			return HealthCheckResponse.up("Database connection health check");
		} else {
			LOG.warning("Liveness: DOWN");
			return HealthCheckResponse.down("Database connection health check");
		}
	}
}
