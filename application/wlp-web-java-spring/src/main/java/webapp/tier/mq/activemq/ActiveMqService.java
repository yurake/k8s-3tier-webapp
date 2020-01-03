package webapp.tier.mq.activemq;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class ActiveMqService {

	Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
	private static String msg = GetConfig.getResourceBundle("common.message");
	private static String splitkey = GetConfig.getResourceBundle("activemq.split.key");
	Connection con = null;
	QueueConnection qcon = null;

	public Connection getConnection() throws Exception {
		InitialContext ic = new InitialContext();
		ConnectionFactory cf = (ConnectionFactory) ic.lookup("jms/QueueConnectionFactory");
		return cf.createConnection();
	}

	public QueueConnection getQueueConnection() throws Exception {
		InitialContext ic = new InitialContext();
		QueueConnectionFactory cf = (QueueConnectionFactory) ic.lookup("jms/QueueConnectionFactory");
		return cf.createQueueConnection();
	}

	public Queue getQueue() throws Exception {
		InitialContext ic = new InitialContext();
		return (Queue) ic.lookup("jms/ActiveMQueue");
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

		try {
			qcon = getQueueConnection();
			qcon.start();

			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qsender = qsession.createSender(getQueue());

			TextMessage message = qsession.createTextMessage(body);
			qsender.send(message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qsender.close();
			qsession.close();
			qcon.close();
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
			qreceiver = qsession.createReceiver(getQueue());

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
			qreceiver.close();
			qsession.close();
			qcon.close();
		}
		return fullmsg;
	}

	public String putProducerActiveMq() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(msg);
		String body = buf.toString();

		con = getConnection();
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
			con = getConnection();
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

	public String publishActiveMq() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		try {
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(msg);
			String body = buf.toString();

			con = getConnection();
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage message = session.createTextMessage(body);
			session.createProducer(getQueue()).send(message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return fullmsg;
	}

}
