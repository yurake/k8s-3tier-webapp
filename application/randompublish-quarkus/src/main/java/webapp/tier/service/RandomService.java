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

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public String deliverrandom(int id) {
		String response;
		switch (id) {
		case 0:
			response = stringBuild("Call: ActiveMQ Publish", activemqresource.publish());
			break;
		case 1:
			response = stringBuild("Call: RabbitMQ Publish", rabbitmqresource.publish());
			break;
		case 2:
			response = stringBuild("Call: Redis Publish", redisresource.publish());
			break;
		case 3:
			response = stringBuild("Call: Postgres Publish", postgresresource.insert());
			break;
		case 4:
			response = stringBuild("Call: Hazelcast Publish",
					hazelcastresource.publish());
			break;
		case 5:
			response = stringBuild("Call: Mongodb Publish", mongodbResource.insert());
			break;
		default:
			logger.log(Level.SEVERE, "random Error.");
			throw new IllegalArgumentException("random error");
		}
		logger.log(Level.INFO, response);
		return response;
	}

	public int getNum(int num) {
		return (int) (Math.random() * num);
	}

	public String stringBuild(String subject, String response) {
		logger.log(Level.INFO, subject);
		StringBuilder buf = new StringBuilder();
		buf.append(subject);
		buf.append(": ");
		buf.append(response);
		return buf.toString();
	}
}
