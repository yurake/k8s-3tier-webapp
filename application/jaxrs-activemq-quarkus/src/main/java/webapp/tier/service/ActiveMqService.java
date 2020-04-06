package webapp.tier.service;

import java.util.Objects;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class ActiveMqService implements Messaging {

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("activemq.url", String.class);
	private static String username = ConfigProvider.getConfig().getValue("activemq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("activemq.password", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.splitkey", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("activemq.queue.name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("activemq.topic.name", String.class);

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());

	@Override
	public String putMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		try (
				ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, username, password);
				Connection conn = cf.createConnection();
				Session session = conn.createSession(Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(session.createQueue(queuename));) {
			conn.start();
			producer.send(session.createTextMessage(body));
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
		try (
				ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, username, password);
				Connection conn = cf.createConnection();
				Session session = conn.createSession(Session.AUTO_ACKNOWLEDGE);
				MessageConsumer consumer = session.createConsumer(session.createQueue(queuename));) {
			conn.start();
			TextMessage resp = (TextMessage) consumer.receive(1000);
			if (Objects.isNull(resp)) {
				msgbean.setFullmsg("No Data");
			} else {
				msgbean.setFullmsgWithType(msgbean.splitBody(resp.getText(), splitkey), "Get");
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
		try (
				ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, username, password);
				Connection conn = cf.createConnection();
				Session session = conn.createSession(Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(session.createTopic(topicname));) {
			conn.start();
			producer.send(session.createTextMessage(body));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}
}
