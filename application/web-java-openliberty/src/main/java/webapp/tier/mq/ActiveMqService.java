package webapp.tier.mq;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class ActiveMqService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private static String msg = GetConfig.getResourceBundle("common.message");
	private static String jndiqcf = GetConfig.getResourceBundle("activemq.jndi.qcf");
	private static String jndiq = GetConfig.getResourceBundle("activemq.jndi.q");
	private static String jndit = GetConfig.getResourceBundle("activemq.jndi.t");
	private static String pubmessage = GetConfig.getResourceBundle("activemq.publisher.message");
	private static String splitkey = GetConfig.getResourceBundle("activemq.split.key");
	Connection conn = null;
	Session session = null;

	private 	InitialContext getInitialContext() throws NamingException {
		return new InitialContext();
	}

	public Session getConnection() throws Exception {
		ConnectionFactory cf = (ConnectionFactory) getInitialContext().lookup(jndiqcf);
		conn = cf.createConnection();
		conn.start();
		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return session;
	}

	public Queue getQueue() throws Exception {
		return (Queue) getInitialContext().lookup(jndiq);
	}

	public Topic getTopic() throws Exception {
		return (Topic) getInitialContext().lookup(jndit);
	}

	public void close() throws JMSException {
		if (session != null) {
			session.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public String putActiveMq() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		try {
			StringBuilder buf = new StringBuilder();
			String body = buf.append(id).append(splitkey).append(msg).toString();
			getConnection();
			TextMessage message = session.createTextMessage(body);
			Queue q = getQueue();
			session.createProducer(q).send(message);
			fullmsg = "Set id: " + id + ", msg: " + msg;
			logger.info(fullmsg);
		} finally {
			close();
		}
		return fullmsg;
	}

	public String getActiveMq() throws Exception {
		String fullmsg = "No Data";

		try {
			getConnection();
			MessageConsumer consumer = session.createConsumer(getQueue());
			TextMessage message = (TextMessage) consumer.receive(1000);

			if (Objects.nonNull(message)) {
				String[] body = message.getText().split(splitkey, 0);
				fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
			}
			logger.info(fullmsg);
		} finally {
			close();
		}
		return fullmsg;
	}

	public String publishActiveMq() throws Exception {
		String fullmsg = "Error";
		String id = String.valueOf(CreateId.createid());

		try {
			StringBuilder buf = new StringBuilder();
			String body = buf.append(id).append(splitkey).append(pubmessage).toString();
			getConnection();
			TextMessage message = session.createTextMessage(body);
			session.createProducer(getTopic()).send(message);
			fullmsg = "Publish id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} finally {
			close();
		}
		return fullmsg;
	}

}
