package webapp.tier.schedule;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import webapp.tier.resource.DeliverResource;
import webapp.tier.resource.MongodbResource;
import webapp.tier.resource.MysqlResource;
import webapp.tier.resource.PostgresResource;

@ApplicationScoped
public class CallRandomPublshSchedule {

	@Inject
	@RestClient
	DeliverResource deliverrsc;

	@Inject
	@RestClient
	PostgresResource postgresrsc;

	@Inject
	@RestClient
	MysqlResource mysqlrsc;

	@Inject
	@RestClient
	MongodbResource mongodbrsc;

	private static final Logger LOG = Logger.getLogger(CallRandomPublshSchedule.class.getSimpleName());

	@Scheduled(every = "10s")
	void callRandomPublsh() {
		String response;
		LOG.info("Call: Random Publish");
		response = deliverrsc.random();
		LOG.info(response);
	}

	@Scheduled(every = "10m")
	void callDeleteDbs() {
		LOG.info("Call: Delete Postgres");
		LOG.info(postgresrsc.delete());
		LOG.info("Call: Delete Mysql");
		LOG.info(mysqlrsc.delete());
		LOG.info("Call: Delete Mongodb");
		LOG.info(mongodbrsc.delete());
	}

}
