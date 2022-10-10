package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.reactive.messaging.annotations.Merge;

@ApplicationScoped
public class KafkaSubscribeService {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Incoming("message")
	@Merge(Merge.Mode.MERGE)
	public void process(String message) {
		logger.log(Level.INFO, "Received: {0}", message);
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
