package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.resource.DeliverResource;

@ApplicationScoped
public class KafkaService {

	@Inject
	@RestClient
	DeliverResource deliversvc;

	private static final Logger LOG = Logger.getLogger(KafkaService.class.getSimpleName());

	@Incoming("message")
	public void process(String message) {
		LOG.log(Level.INFO, "Received: {0}", message);
		String response = deliversvc.random();
		LOG.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
