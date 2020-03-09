package webapp.tier.service;

import java.util.Objects;
import java.util.logging.Logger;

import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

public class ActiveMqService implements Messaging {

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.splitkey", String.class);
	private static String url = ConfigProvider.getConfig().getValue("activemq.url", String.class);
	private static String username = ConfigProvider.getConfig().getValue("activemq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("activemq.password", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("activemq.queue.name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("activemq.opic.name", String.class);
	QueueConnection qcon = null;
	TopicConnection tcon = null;

	private QueueConnection getQueueConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
		cf.setUserName(username);
		cf.setPassword(password);
		return cf.createQueueConnection();
	}

	private TopicConnection getTopicConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
		cf.setUserName(username);
		cf.setPassword(password);
		return cf.createTopicConnection();
	}

	private void closeQueueConnection() throws Exception {
		if (tcon != null) {
			tcon.close();
		}
	}

	private void closeTopicConnection() throws Exception {
		if (qcon != null) {
			qcon.close();
		}
	}

	@Override
	public String putMsg() throws Exception {
		QueueSession qsession = null;
		QueueSender qsender = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			qcon = getQueueConnection();
			qcon.start();

			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qsender = qsession.createSender(qsession.createQueue(queuename));

			TextMessage message = qsession.createTextMessage(body);
			qsender.send(message);

			msgbean.setFullmsgWithType(msgbean, "Publish");
			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			if (qsender != null) {
				qsender.close();
			}
			if (qsession != null) {
				qsession.close();
			}
			closeQueueConnection();
		}
	}

	@Override
	public String getMsg() throws Exception {
		QueueSession qsession = null;
		QueueReceiver qreceiver = null;
		MsgBeanUtils msgbean = new MsgBeanUtils();

		try {
			qcon = getQueueConnection();
			qcon.start();

			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qreceiver = qsession.createReceiver(qsession.createQueue(queuename));

			TextMessage resp = (TextMessage) qreceiver.receive(1000);

			if (Objects.isNull(resp)) {
				msgbean.setFullmsg("No Data");
			} else {
				msgbean.setFullmsgWithType(msgbean.splitBody(resp.getText(), splitkey), "Get");
			}

			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			if (qreceiver != null) {
				qreceiver.close();
			}
			if (qsession != null) {
				qsession.close();
			}
			closeQueueConnection();
		}
	}

	@Override
	public String publishMsg() throws Exception {
		TopicSession session = null;
		TopicPublisher publisher = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {

			tcon = getTopicConnection();
			session = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			publisher = session.createPublisher(session.createTopic(topicname));
			TextMessage message = session.createTextMessage(body);
			publisher.publish(message);

			msgbean.setFullmsgWithType(msgbean, "Publish");
			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			if (publisher != null) {
				publisher.close();
			}
			if (session != null) {
				session.close();
			}
			closeTopicConnection();
		}
	}
}
