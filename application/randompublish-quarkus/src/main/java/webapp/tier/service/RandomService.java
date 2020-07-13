package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.resource.ActivemqResource;
import webapp.tier.resource.HazelcastResource;
import webapp.tier.resource.MongodbResource;
import webapp.tier.resource.PostgresResource;
import webapp.tier.resource.RabbitmqResource;
import webapp.tier.resource.RedisResource;

@ApplicationScoped
public class RandomService {

	@Inject
	@RestClient
	ActivemqResource activemqresource;

	@Inject
	@RestClient
	HazelcastResource hazelcastresource;

	@Inject
	@RestClient
	RabbitmqResource rabbitmqresource;

	@Inject
	@RestClient
	RedisResource redisresource;

	@Inject
	@RestClient
	PostgresResource postgresresource;

	@Inject
	@RestClient
	MongodbResource mongodbResource;

	private static final Logger LOG = Logger.getLogger(RandomService.class.getSimpleName());

	public String deliverrandom() throws RuntimeException {
		String response;
		int id = (int) (Math.random() * 6);
		switch (id) {
		case 0:
			LOG.log(Level.INFO, "Call: ActiveMQ Publish");
			response = activemqresource.publish();
			break;
		case 1:
			LOG.log(Level.INFO, "Call: RabbitMQ Publish");
			response = rabbitmqresource.publish();
			break;
		case 2:
			LOG.log(Level.INFO, "Call: Redis Publish");
			response = redisresource.publish();
			break;
		case 3:
			LOG.log(Level.INFO, "Call: Postgres Insert");
			response = postgresresource.insert();
			break;
		case 4:
			LOG.log(Level.INFO, "Call: Hazelcast Publish");
			response = hazelcastresource.publish();
			break;
		case 5:
			LOG.log(Level.INFO, "Call: Mongodb Insert");
			response = mongodbResource.insert();
			break;
		default:
			LOG.log(Level.SEVERE, "random Error.");
			throw new RuntimeException("random error");
		}
		LOG.info(response);
		return response;
	}
}
