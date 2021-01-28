package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Session;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ActiveMqSubscribeService extends ActiveMqService {

	@Inject
	ConnectionFactory connectionFactory;

	@Inject
	ActiveMqDeliverConsumer amqdelconsumer;

	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public void run() {
		while (isEnableReceived) {
			try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
					JMSConsumer consumer = context.createConsumer(context.createTopic(topicname))) {
				amqdelconsumer.consume(consumer);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Subscribe Error.", e);
				stopReceived();
			}
		}
	}
}
