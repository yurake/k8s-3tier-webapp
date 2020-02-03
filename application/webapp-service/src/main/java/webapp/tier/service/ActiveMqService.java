package webapp.tier.service;

import java.util.Objects;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.constant.EnumService;
import webapp.tier.util.CreateId;

public class ActiveMqService {

	Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
	private static String message = EnumService.common_message.getString();
	private static String splitkey = EnumService.activemq_splitkey.getString();
	private static String url = EnumService.activemq_url.getString();
	private static String queuename = EnumService.activemq_queue_name.getString();
	private static String topicname = EnumService.activemq_opic_name.getString();
	QueueConnection qcon = null;
	TopicConnection tcon = null;

	public QueueConnection getQueueConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
		return cf.createQueueConnection();
	}

	public TopicConnection getTopicConnection() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
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
			logger.info(fullmsg);

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
			logger.info(fullmsg);

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
			logger.info(fullmsg);

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
