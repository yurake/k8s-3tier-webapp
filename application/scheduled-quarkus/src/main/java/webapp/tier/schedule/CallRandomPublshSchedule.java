package webapp.tier.schedule;

import java.util.Objects;
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
		String pstgresp = postgressvc.delete();
		if(!Objects.isNull(pstgresp)) {
			logger.log(Level.INFO, pstgresp);			
		}
		logger.log(Level.INFO, "Call: Delete Mysql");
		String mysqlresp = mysqlsvc.delete();
		if(!Objects.isNull(mysqlresp)) {
			logger.log(Level.INFO, mysqlresp);			
		}
		logger.log(Level.INFO, "Call: Delete Mongodb");
		String mongoresp = mongodbsvc.delete();
		if(!Objects.isNull(mongoresp)) {
			logger.log(Level.INFO, mongoresp);			
		}
	}

}
