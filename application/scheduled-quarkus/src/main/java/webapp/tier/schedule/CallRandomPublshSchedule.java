package webapp.tier.schedule;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import webapp.tier.service.DeliverService;
import webapp.tier.service.MongodbService;
import webapp.tier.service.MysqlService;
import webapp.tier.service.PostgresService;

@ApplicationScoped
public class CallRandomPublshSchedule {

	@Inject
	@RestClient
	DeliverService deliversvc;

	@Inject
	@RestClient
	PostgresService postgressvc;

	@Inject
	@RestClient
	MysqlService mysqlsvc;

	@Inject
	@RestClient
	MongodbService mongodbsvc;

	private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());

	@Scheduled(every = "10s")
	void callRandomPublsh() {
		String response;
		LOG.info("Call: Random Publish");
		response = deliversvc.random();
		LOG.info(response);
	}

	@Scheduled(every = "10m")
	void callDeleteDbs() {
		LOG.info("Call: Delete Postgres");
		LOG.info(postgressvc.delete());
		LOG.info("Call: Delete Mysql");
		LOG.info(mysqlsvc.delete());
		LOG.info("Call: Delete Mongodb");
		LOG.info(mongodbsvc.delete());
	}

}
