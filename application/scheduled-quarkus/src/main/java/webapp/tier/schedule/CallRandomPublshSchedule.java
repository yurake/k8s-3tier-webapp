package webapp.tier.schedule;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import webapp.tier.service.DeliverService;
import webapp.tier.service.MysqlService;
import webapp.tier.service.PostgresService;

@ApplicationScoped
public class CallRandomPublshSchedule {

	@Inject
	@RestClient
	DeliverService deliversvc;

	@Inject
	@RestClient
	PostgresService postgresrsvc;

	@Inject
	@RestClient
	MysqlService mysqlrsvc;

	private static final Logger LOG = Logger.getLogger(CallRandomPublshSchedule.class.getSimpleName());

	@Scheduled(every = "10s")
	void callRandomPublsh() {
		String response;
		LOG.info("Call: Random Publish");
		response = deliversvc.random();
		LOG.info(response);
	}

	@Scheduled(every = "10m")
	void callDeleteDbs() {
		String response;
		LOG.info("Call: Delete Postgres");
		response = postgresrsvc.delete();
		LOG.info(response);
		response = mysqlrsvc.delete();
		LOG.info(response);
	}

}
