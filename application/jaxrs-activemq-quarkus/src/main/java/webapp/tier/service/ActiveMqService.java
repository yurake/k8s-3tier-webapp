package webapp.tier.service;

import java.util.Objects;
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
	public String putMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createQueue(queuename), context.createTextMessage(body));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws Exception {
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
			e.printStackTrace();
			throw new Exception("Get Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createTopic(topicname), context.createTextMessage(body));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}
}
