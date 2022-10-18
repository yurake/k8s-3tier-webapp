package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class RabbitmqConverter {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "rabbitmq.split.key")
	String splitkey;

	@Incoming("converter")
	@Outgoing("message")
	public Multi<String> convert(String message) {
		logger.log(Level.INFO, "Received: {0}", message);
		return Multi.createFrom().items(message)
				.onCompletion()
				.invoke(() -> logger.log(Level.INFO, "Completed transfer messages."))
				.onFailure()
				.retry().atMost(3)
				.invoke(() -> logger.log(Level.WARNING,
						"Retried send messages."))
				.onFailure()
				.recoverWithCompletion()
				.invoke(() -> logger.log(Level.WARNING,
						"Recovered and completed send messages."));

	}

}
