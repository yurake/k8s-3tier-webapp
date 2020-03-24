package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.resource.DeliverResource;

@ApplicationScoped
public class RandomService {

	@Inject
	@RestClient
	DeliverResource deliverresource;

	Logger logger = LoggerFactory.getLogger(RandomService.class);

	public String deliverrandom() throws Exception {
		String response;
		int id = (int) (Math.random() * 5);
		switch (id) {
		case 0:
			logger.info("Call: ActiveMQ Publish");
			response = deliverresource.activemq();
			break;
		case 1:
			logger.info("Call: RabbitMQ Publish");
			response = deliverresource.rabbitmq();
			break;
		case 2:
			logger.info("Call: Redis Publish");
			response = deliverresource.redis();
			break;
		case 3:
			logger.info("Call: Postgres Insert");
			response = deliverresource.postgres();
			break;
		case 4:
			logger.info("Call: Hazelcast Publish");
			response = deliverresource.hazelcast();
			break;
		default:
			throw new Exception("random error");
		}
		logger.info(response);
		return response;
	}
}
