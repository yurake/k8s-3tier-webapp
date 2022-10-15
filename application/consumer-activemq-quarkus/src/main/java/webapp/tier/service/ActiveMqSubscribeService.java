package webapp.tier.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Session;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ActiveMqSubscribeService implements Runnable {

	@Inject
	ConnectionFactory connectionFactory;

	@Inject
	ActiveMqDeliverConsumer amqdelconsumer;

	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor();
	private static boolean isEnableReceived = true;
	
	void onStart(@Observes StartupEvent ev) {
		scheduler.scheduleWithFixedDelay(this, 0L, 5L, TimeUnit.SECONDS);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		stopReceived();
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
	}
	
	public static void stopReceived() {
		ActiveMqSubscribeService.isEnableReceived = false;
	}

	public void run() {
		while (isEnableReceived) {
			try (JMSContext context = connectionFactory
					.createContext(Session.AUTO_ACKNOWLEDGE);
					JMSConsumer consumer = context
							.createConsumer(context.createTopic(topicname))) {
				amqdelconsumer.consume(consumer);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Subscribe Error.", e);
			}
		}
	}
}
