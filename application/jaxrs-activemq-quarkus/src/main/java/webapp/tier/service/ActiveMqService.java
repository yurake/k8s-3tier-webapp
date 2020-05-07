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

import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

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
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createTopic(topicname), context.createTextMessage(body));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}
}
