package webapp.tier.service.generator;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class MessageGenerator {

	private static final Logger LOG = Logger.getLogger(MessageGenerator.class);

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "kafka.splitkey")
	String splitkey;

	@ConfigProperty(name = "kafka.generate.message.period.seconds")
	long period;

	@Outgoing("message")
	@Broadcast
	public Flowable<String> generate() {

		return Flowable.interval(period, TimeUnit.SECONDS)
				.map(tick -> {
					MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
					msgbean.setFullmsgWithType(msgbean, "Generate");
					LOG.info(msgbean.getFullmsg());
					return msgbean.createBody(msgbean, splitkey);
				});
	}
}
