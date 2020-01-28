package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RandomService {

	@Inject
	@RestClient
	DeliverService deliversvc;

	Logger logger = LoggerFactory.getLogger(RandomService.class);

	public String deliverrandom() throws Exception {
		String response;
		int id = (int) (Math.random() * 4);
		switch (id) {
		case 0:
			logger.info("Call: ActiveMQ Publish");
			response = deliversvc.activemq();
			break;
		case 1:
			logger.info("Call: RabbitMQ Publish");
			response = deliversvc.rabbitmq();
			break;
		case 2:
			logger.info("Call: Redis Publish");
			response = deliversvc.redis();
			break;
		case 3:
			logger.info("Call: Postgres Insert");
			response = deliversvc.postgres();
			break;
		default:
			throw new Exception();
		}
		logger.info(response);
		return response;
	}
}
