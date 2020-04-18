package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Session;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class ActiveMqService implements Messaging {

	@Inject
	ConnectionFactory connectionFactory;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "activemq.splitkey")
	String splitkey;
	@ConfigProperty(name = "activemq.queue.name")
	String queuename;
	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());

	@Override
	public String putMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createQueue(queuename), context.createTextMessage(body));
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws RuntimeException {
		MsgBeanUtils msgbean = new MsgBeanUtils();
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
			JMSConsumer consumer = context.createConsumer(context.createQueue(queuename))) {
			String resp = consumer.receiveBody(String.class);

			if (Objects.isNull(resp)) {
				msgbean.setFullmsg("No Data");
			} else {
				msgbean.setFullmsgWithType(msgbean.splitBody(resp, splitkey), "Get");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createTopic(topicname), context.createTextMessage(body));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}
}
