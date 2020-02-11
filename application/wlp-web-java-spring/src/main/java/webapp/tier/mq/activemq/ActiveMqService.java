package webapp.tier.mq.activemq;

import java.util.Objects;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class ActiveMqService {

	Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
	private static String msg = GetConfig.getResourceBundle("common.message");
	private static String pubmessage = GetConfig.getResourceBundle("activemq.publisher.message");
	private static String splitkey = GetConfig.getResourceBundle("activemq.split.key");
	QueueConnection qcon = null;
	TopicConnection tcon = null;

	public QueueConnection getQueueConnection() throws Exception {
		InitialContext ic = new InitialContext();
		QueueConnectionFactory cf = (QueueConnectionFactory) ic.lookup("jms/QueueConnectionFactory");
		return cf.createQueueConnection();
	}

	public TopicConnection getTopicConnection() throws Exception {
		InitialContext ic = new InitialContext();
		TopicConnectionFactory cf = (TopicConnectionFactory) ic.lookup("jms/QueueConnectionFactory");
		return cf.createTopicConnection();
	}

	public Queue getQueue() throws Exception {
		InitialContext ic = new InitialContext();
		return (Queue) ic.lookup("jms/ActiveMQueue");
	}

	public Topic getTopic() throws Exception {
		InitialContext ic = new InitialContext();
		return (Topic) ic.lookup("jms/ActiveMQTopic");
	}

	public String putActiveMq() throws Exception {
		String fullmsg = null;
		QueueSession qsession = null;
		QueueSender qsender = null;
		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(msg);
		String body = buf.toString();

		qcon = getQueueConnection();
		qcon.start();

		qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		qsender = qsession.createSender(getQueue());

		TextMessage message = qsession.createTextMessage(body);
		qsender.send(message);
		fullmsg = "Set id: " + id + ", msg: " + message;
		logger.info(fullmsg);

		if (qsender != null) {
			qsender.close();
		}
		if (qsession != null) {
			qsession.close();
		}
		if (qcon != null) {
			qcon.close();
		}
		return fullmsg;
	}

	public String getActiveMq() throws Exception {
		String fullmsg = null;
		QueueSession qsession = null;
		QueueReceiver qreceiver = null;

		qcon = getQueueConnection();
		qcon.start();

		qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		qreceiver = qsession.createReceiver(getQueue());

		TextMessage message = (TextMessage) qreceiver.receive(1000);

		if (Objects.isNull(message)) {
			fullmsg = "No Data";
		} else {
			String[] body = message.getText().split(splitkey, 0);
			fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
		}
		logger.info(fullmsg);

		if (qreceiver != null) {
			qreceiver.close();
		}
		if (qsession != null) {
			qsession.close();
		}
		if (qcon != null) {
			qcon.close();
		}
		return fullmsg;
	}

	/**
	public String putProducerActiveMq() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(msg);
		String body = buf.toString();

		con = getQueueConnection();
		con.start();
		Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		TextMessage message = session.createTextMessage(body);
		session.createProducer(getQueue()).send(message);
		fullmsg = "Set id: " + id + ", msg: " + message;
		logger.info(fullmsg);

		return fullmsg;
	}

	public String getConsumerActiveMq() throws Exception {
		String fullmsg = null;

		try {
			con = getQueueConnection();
			con.start();
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Message message = session.createConsumer(getQueue()).receive();

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String[] body = textMessage.getText().split(splitkey, 0);
				fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
				logger.info(fullmsg);
			} else {
				fullmsg = "No Data";
				logger.info(fullmsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return fullmsg;
	}
	**/

	public String publishActiveMq() throws Exception {
		String fullmsg = "Error";
		TopicSession session = null;
		TopicPublisher publisher = null;
		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(pubmessage);
		String body = buf.toString();

		tcon = getTopicConnection();
		session = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		publisher = session.createPublisher(getTopic());
		TextMessage message = session.createTextMessage(body);
		publisher.publish(message);
		fullmsg = "Set id: " + id + ", msg: " + message;
		logger.info(fullmsg);

		if (publisher != null) {
			publisher.close();
		}
		if (session != null) {
			session.close();
		}
		if (tcon != null) {
			tcon.close();
		}
		return fullmsg;
	}

}
