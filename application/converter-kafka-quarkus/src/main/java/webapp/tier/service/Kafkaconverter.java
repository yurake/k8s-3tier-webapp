package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class Kafkaconverter {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Incoming("converter")
	@Outgoing("message")
	public Multi<String> convert(String message) {
		logger.log(Level.INFO, "Received: {0}", message);
		return Multi.createFrom().items(message)
				.onFailure().recoverWithCompletion()
				.onCompletion()
				.invoke(() -> logger.log(Level.INFO, "Completed transfer messages."));
	}
}
