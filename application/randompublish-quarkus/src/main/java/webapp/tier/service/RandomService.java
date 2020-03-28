package webapp.tier.service;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.resource.ActivemqResource;
import webapp.tier.resource.HazelcastResource;
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

	private static final Logger LOG = Logger.getLogger(RandomService.class.getSimpleName());

	public String deliverrandom() throws Exception {
		String response;
		int id = (int) (Math.random() * 5);
		switch (id) {
		case 0:
			LOG.info("Call: ActiveMQ Publish");
			response = activemqresource.publish();
			break;
		case 1:
			LOG.info("Call: RabbitMQ Publish");
			response = rabbitmqresource.publish();
			break;
		case 2:
			LOG.info("Call: Redis Publish");
			response = redisresource.publish();
			break;
		case 3:
			LOG.info("Call: Postgres Insert");
			response = postgresresource.insert();
			break;
		case 4:
			LOG.info("Call: Hazelcast Publish");
			response = hazelcastresource.publish();
			break;
		default:
			throw new Exception("random error");
		}
		LOG.info(response);
		return response;
	}
}
