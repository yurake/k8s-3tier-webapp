package webapp.tier.schedule;

import java.util.logging.Level;
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

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Scheduled(every = "10s")
	void callRandomPublsh() {
		String response;
		logger.log(Level.INFO, "Call: Random Publish");
		response = deliversvc.random();
		logger.log(Level.INFO, response);
	}

	@Scheduled(every = "10m")
	void callDeleteDbs() {
		logger.log(Level.INFO, "Call: Delete Postgres");
		logger.log(Level.INFO, postgressvc.delete());
		logger.log(Level.INFO, "Call: Delete Mysql");
		logger.log(Level.INFO, mysqlsvc.delete());
		logger.log(Level.INFO, "Call: Delete Mongodb");
		logger.log(Level.INFO, mongodbsvc.delete());
	}

}
