package webapp.tier.service.generator;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.reactivex.Flowable;
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
	public Flowable<String> generate() {

		return Flowable.interval(period, TimeUnit.SECONDS)
				.onBackpressureDrop()
				.map(tick -> {
					MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Generate");
					logger.info(msgbean.getFullmsg());
					return MsgUtils.createBody(msgbean, splitkey);
				});
	}
}
