package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class KafkaSubscribeService {

	@Inject
	@RestClient
	KafkaDeliverService deliversvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Incoming("message")
	public void process(String message) {
		logger.log(Level.INFO, "Received: {0}", message);
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
