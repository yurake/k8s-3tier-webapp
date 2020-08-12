package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.service.client.ActivemqClientService;
import webapp.tier.service.client.HazelcastClientService;
import webapp.tier.service.client.MongodbClientService;
import webapp.tier.service.client.PostgresClientService;
import webapp.tier.service.client.RabbitmqClientService;
import webapp.tier.service.client.RedisClientService;

@ApplicationScoped
public class RandomService {

	@Inject
	@RestClient
	ActivemqClientService activemqresource;

	@Inject
	@RestClient
	HazelcastClientService hazelcastresource;

	@Inject
	@RestClient
	RabbitmqClientService rabbitmqresource;

	@Inject
	@RestClient
	RedisClientService redisresource;

	@Inject
	@RestClient
	PostgresClientService postgresresource;

	@Inject
	@RestClient
	MongodbClientService mongodbResource;

	private static final Logger LOG = Logger.getLogger(RandomService.class.getSimpleName());

	public String deliverrandom(Integer id) throws Exception {
		String response;
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
			throw new IllegalArgumentException("random error");
		}
		LOG.info(response);
		return response;
	}

	public int getNum(Integer i) {
		 return (int) (Math.random() * i);
	}
}
