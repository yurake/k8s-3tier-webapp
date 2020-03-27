package webapp.tier.service;

import java.util.Objects;
import java.util.logging.Logger;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
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

	private QueueConnection getQueueConnection() throws Exception {
		QueueConnectionFactory cf = new ActiveMQConnectionFactory(url);
		return cf.createQueueConnection(username, password);
	}

	private TopicConnection getTopicConnection() throws Exception {
		TopicConnectionFactory cf = new ActiveMQConnectionFactory(url);
		return cf.createTopicConnection(username, password);
	}

	private void closeQueueConnection(QueueConnection qcon) throws Exception {
		if (qcon != null) {
			LOG.fine("Close Queue Connection");
			qcon.close();
		}
	}

	private void closeTopicConnection(TopicConnection tcon) throws Exception {
		if (tcon != null) {
			LOG.fine("Close Topic Connection");
			tcon.close();
		}
	}

	@Override
	public String putMsg() throws Exception {
		QueueConnection qcon = null;
		QueueSession qsession = null;
		QueueSender qsender = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			LOG.fine("Start Get Coonection");
			qcon = getQueueConnection();
			qcon.start();

			LOG.fine("Start Queue Session");
			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			LOG.fine("Start Create Sender");
			qsender = qsession.createSender(qsession.createQueue(queuename));

			TextMessage message = qsession.createTextMessage(body);
			LOG.fine("Start Send ...");
			qsender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			if (qsender != null) {
				LOG.fine("Close Sender");
				qsender.close();
			}
			if (qsession != null) {
				LOG.fine("Close Queue Session");
				qsession.close();
			}
			closeQueueConnection(qcon);
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws Exception {
		QueueConnection qcon = null;
		QueueSession qsession = null;
		QueueReceiver qreceiver = null;
		MsgBeanUtils msgbean = new MsgBeanUtils();

		try {
			LOG.fine("Start Get Coonection");
			qcon = getQueueConnection();
			qcon.start();

			LOG.fine("Start Queue Session");
			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			LOG.fine("Start Create Receiver");
			qreceiver = qsession.createReceiver(qsession.createQueue(queuename));

			LOG.fine("Start Receive ...");
			TextMessage resp = (TextMessage) qreceiver.receive(1000);

			if (Objects.isNull(resp)) {
				msgbean.setFullmsg("No Data");
			} else {
				msgbean.setFullmsgWithType(msgbean.splitBody(resp.getText(), splitkey), "Get");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			if (qreceiver != null) {
				LOG.fine("Close Receiver");
				qreceiver.close();
			}
			if (qsession != null) {
				LOG.fine("Close Queue Session");
				qsession.close();
			}
			closeQueueConnection(qcon);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws Exception {
		TopicConnection tcon = null;
		TopicSession session = null;
		TopicPublisher publisher = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			LOG.fine("Start Topic Coonection");
			tcon = getTopicConnection();

			LOG.fine("Start Topic Session");
			session = tcon.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			LOG.fine("Start Create Publisher");
			publisher = session.createPublisher(session.createTopic(topicname));
			TextMessage message = session.createTextMessage(body);

			LOG.fine("Start Publish ...");
			publisher.publish(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			if (publisher != null) {
				LOG.fine("Close Publisher");
				publisher.close();
			}
			if (session != null) {
				LOG.fine("Close Topic Session");
				session.close();
			}
			closeTopicConnection(tcon);
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public boolean isActive() {
		boolean status = false;
		QueueConnection qcon = null;
		QueueSession qsession = null;
		QueueSender qsender = null;
		try {
			qcon = getQueueConnection();
			qcon.start();
			qsession = qcon.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			qsender = qsession.createSender(qsession.createQueue(queuename));
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (qsender != null) {
					LOG.fine("Close Sender");
					qsender.close();
				}
				if (qsession != null) {
					LOG.fine("Close Queue Session");
					qsession.close();
				}
				closeQueueConnection(qcon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}
}
