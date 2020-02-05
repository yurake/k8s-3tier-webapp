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

import webapp.tier.util.CreateId;

public class ActiveMqService {

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common_message", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq_splitkey", String.class);
	private static String url = ConfigProvider.getConfig().getValue("activemq_url", String.class);
	private static String username = ConfigProvider.getConfig().getValue("activemq_username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("activemq_password", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("activemq_queue_name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("activemq_opic_name", String.class);
	QueueConnection qcon = null;
	TopicConnection tcon = null;

	public QueueConnection getQueueConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
		cf.setUserName(username);
		cf.setPassword(password);
		return cf.createQueueConnection();
	}

	public TopicConnection getTopicConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
		cf.setUserName(username);
		cf.setPassword(password);
		return cf.createTopicConnection();
	}

	public String putActiveMq() throws Exception {
		String fullmsg = null;
		QueueSession qsession = null;
		QueueSender qsender = null;
		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		String body = buf.toString();

		try {
			qcon = getQueueConnection();
			qcon.start();

			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qsender = qsession.createSender(qsession.createQueue(queuename));

			TextMessage message = qsession.createTextMessage(body);
			qsender.send(message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (qsender != null) {
				qsender.close();
			} else if (qsession != null) {
				qsession.close();
			} else if (qcon != null) {
				qcon.close();
			}
		}
		return fullmsg;
	}

	public String getActiveMq() throws Exception {
		String fullmsg = null;
		QueueSession qsession = null;
		QueueReceiver qreceiver = null;

		try {
			qcon = getQueueConnection();
			qcon.start();

			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qreceiver = qsession.createReceiver(qsession.createQueue(queuename));

			TextMessage message = (TextMessage) qreceiver.receive(1000);

			if (Objects.isNull(message)) {
				fullmsg = "No Data";
			} else {
				String[] body = message.getText().split(splitkey, 0);
				fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
			}
			LOG.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (qreceiver != null) {
				qreceiver.close();
			} else if (qsession != null) {
				qsession.close();
			} else if (qcon != null) {
				qcon.close();
			}
		}
		return fullmsg;
	}

	public String publishActiveMq() throws Exception {
		String fullmsg = null;
		TopicSession session = null;
		TopicPublisher publisher = null;
		String id = String.valueOf(CreateId.createid());

		try {
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(message);
			String body = buf.toString();

			tcon = getTopicConnection();
			session = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			publisher = session.createPublisher(session.createTopic(topicname));
			TextMessage message = session.createTextMessage(body);
			publisher.publish(message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (publisher != null) {
					publisher.close();
				} else if (session != null) {
					session.close();
				} else if (tcon != null) {
					tcon.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fullmsg;
	}

}
