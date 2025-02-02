package webapp.tier.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Session;

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

	@Override
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
