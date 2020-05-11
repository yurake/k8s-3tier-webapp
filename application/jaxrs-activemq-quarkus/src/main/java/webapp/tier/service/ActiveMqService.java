package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Messaging;
import webapp.tier.service.socket.ActiveMqSocket;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class ActiveMqService implements Messaging, Runnable {

	@Inject
	ConnectionFactory connectionFactory;

	@Inject
	ActiveMqSocket amqsock;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "activemq.splitkey")
	String splitkey;
	@ConfigProperty(name = "activemq.queue.name")
	String queuename;
	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("Subscribe is stopping...");
	}

	@Override
	public MsgBean putMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createQueue(queuename), context.createTextMessage(body));
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean getMsg() throws RuntimeException {
		MsgBean msgbean = null;
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
			JMSConsumer consumer = context.createConsumer(context.createQueue(queuename))) {
			String resp = consumer.receiveBody(String.class);

			if (Objects.isNull(resp)) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(resp, splitkey);
				msgbean.setFullmsg("Get");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean publishMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);
		LOG.info(msgbean.getFullmsg());
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createTopic(topicname), context.createTextMessage(body));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		}
		return msgbean;
	}

	@Override
	public void run() {
		while (isEnableReceived) {
			try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
					JMSConsumer consumer = context.createConsumer(context.createTopic(topicname))) {
				LOG.info("Ready for receive message...");
				Message message = consumer.receive();

				TextMessage textMessage = (TextMessage) message;
				MsgBean msgbean = MsgUtils.splitBody(textMessage.getText(), splitkey);
				msgbean.setFullmsg("Received");
				LOG.log(Level.INFO, msgbean.getFullmsg());
				amqsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
				msgbean.setFullmsg("Broadcast");
				LOG.log(Level.INFO, msgbean.getFullmsg());
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Publish Error.", e);
			}
		}
	}

	public static void startReceived() {
		ActiveMqService.isEnableReceived = true;
	}

	public static void stopReceived() {
		ActiveMqService.isEnableReceived = false;
	}
}
