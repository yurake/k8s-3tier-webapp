package webapp.tier.service.generator;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class MessageGenerator {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

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
				.map(tick -> {
					return generateMessgae();
				})
				.onFailure().recoverWithCompletion();
	}
	
	private String generateMessgae() {
		MsgBean msgbean = null;
		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Generate");
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.FATAL, "Create Id Error.", e);
		}
		logger.info(msgbean.getFullmsg());
		return MsgUtils.createBody(msgbean, splitkey);
	}
}
