package webapp.tier.schedule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	Logger logger = LoggerFactory.getLogger(CallRandomPublshSchedule.class);

	@Scheduled(every = "10s")
	void callRandomPublsh() {
		String response;
		logger.info("Call: Random Publish");
		response = deliversvc.random();
		logger.info(response);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	void callDeleteDbs() {
		String response;
		logger.info("Call: Delete Postgres");
		response = postgresrsvc.delete();
		logger.info(response);
		response = mysqlrsvc.delete();
		logger.info(response);
	}

}
