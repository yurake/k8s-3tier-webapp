package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class ActiveMqService implements Messaging, Runnable {

	@Inject
	ConnectionFactory connectionFactory;

	@Inject
	ActiveMqConsumer amqconsumer;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "activemq.split.key")
	String splitkey;
	@ConfigProperty(name = "activemq.queue.name")
	String queuename;
	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
	}

	public static void startReceived() {
		ActiveMqService.isEnableReceived = true;
	}

	public static void stopReceived() {
		ActiveMqService.isEnableReceived = false;
	}

	@Override
	public MsgBean putMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);
		try (JMSContext context = createContext()) {
			context.createProducer().send(context.createQueue(queuename),
					context.createTextMessage(body));
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean getMsg() throws RuntimeException {
		MsgBean msgbean = new MsgBean(0, "No Data.", "Get");
		try (JMSConsumer consumer = createConsumerQueue(createContext())) {
			String resp = consumer.receiveBody(String.class);

			if (!Objects.isNull(resp)) {
				msgbean = MsgUtils.splitBody(resp, splitkey);
				msgbean.setFullmsg("Get");
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean publishMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);
		try (JMSContext context = createContext()) {
			context.createProducer().send(context.createTopic(topicname),
					context.createTextMessage(body));
		} 
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public void run() {
		while (isEnableReceived) {
			try (JMSConsumer consumer = createConsumerTopic(createContext())) {
				amqconsumer.consume(consumer);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Subscribe Error.", e);
				stopReceived();
			}
		}
	}

	private JMSContext createContext() {
		return connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
	}

	private JMSConsumer createConsumerTopic(JMSContext context) {
		return context.createConsumer(context.createTopic(topicname));
	}
	
	private JMSConsumer createConsumerQueue(JMSContext context) {
		return context.createConsumer(context.createQueue(queuename));
	}
}
