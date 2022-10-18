package webapp.tier.service.generator;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class MessageGenerator {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "kafka.splitkey")
	String splitkey;

	@ConfigProperty(name = "kafka.generate.message.period.seconds")
	long period;

	@Outgoing("message")
	@Broadcast
	public Multi<String> generate() {
		return Multi.createFrom().ticks().every(Duration.ofSeconds(period))
				.map(tick -> generateMessgae())
				.onCompletion()
				.invoke(() -> logger.log(Level.INFO, "Completed send messages."))
				.onFailure()
				.retry().atMost(3)
				.invoke(() -> logger.log(Level.WARNING,
						"Retried send messages."))
				.onFailure()
				.recoverWithCompletion()
				.invoke(() -> logger.log(Level.WARNING,
						"Recovered and completed send messages."));
	}

	private String generateMessgae() {
		MsgBean msgbean = errormsg;
		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Generate");
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Create Id Error.", e);
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return MsgUtils.createBody(msgbean, splitkey);
	}
}
