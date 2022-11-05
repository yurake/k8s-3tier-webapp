package webapp.tier.service;

import java.net.URI;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Multi;
import webapp.tier.bean.MsgBean;

@ApplicationScoped
@Startup
public class RedisDeliverSubscriber implements Consumer<RedisDeliverNotification> {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private static String channel = ConfigProvider.getConfig().getValue("redis.channel",
			String.class);

	private final PubSubCommands<RedisDeliverNotification> pub;
	private final PubSubCommands.RedisSubscriber subscriber;

	@RestClient
	RedisDeliverService deliversvc;

	public RedisDeliverSubscriber(RedisDataSource ds) {
		pub = ds.pubsub(RedisDeliverNotification.class);
		subscriber = pub.subscribe(channel, this);

		deliversvc = RestClientBuilder.newBuilder()
				.baseUri(URI.create("https://stage.code.quarkus.io/api"))
				.build(RedisDeliverService.class);
		logger.log(Level.INFO, "Subscribing...");
	}

	@Override
	public void accept(RedisDeliverNotification notification) {
		MsgBean msgbean = notification.msgbean;
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		Multi<String> response = deliversvc.random();
		response.subscribe().with(
				result -> logger.log(Level.INFO, "Call Random Publish: {0}", result),
				failure -> logger.log(Level.SEVERE, "Call Random Publish Error: {0}",
						failure.getMessage()));
	}

	@PreDestroy
	public void terminate() {
		logger.log(Level.INFO, "Unsubscibed.");
		subscriber.unsubscribe();
	}
}
