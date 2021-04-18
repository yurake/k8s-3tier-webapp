package webapp.tier.mq;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@Service
public class ActiveMqService {

	@Autowired
	private Queue queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${common.message}")
	private String message;

	@Value("${spring.activemq.broker-url}")
	private String url;

	@Value("${spring.activemq.split.key}")
	private String splitkey;

	@Value("${spring.activemq.queue.name}")
	private String queuename;

	@Value("${spring.activemq.topic.name}")
	private String topicname;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	Connection conn = null;
	Session session = null;

	public MsgBean put() {
		MsgBean msgbean = new MsgBean(message);
		String body = msgbean.createBody(splitkey);
		jmsTemplate.convertAndSend(queuename, body);
		logger.info(msgbean.logMessageOut("put"));
		return msgbean;
	}

	public MsgBean get() throws JMSException {
		MsgBean msgbean = null;
		Message msg = jmsTemplate.receive(queuename);
		if (Objects.isNull(msg)) {
			msgbean = new MsgBean(0, "No Data.");
		} else {
			TextMessage textMessage = (TextMessage) msg;
			String[] body = textMessage.getText().split(splitkey, 0);
			msgbean = new MsgBean(body[0], body[1]);
		}
		logger.info(msgbean.logMessageOut("get"));
		return msgbean;
	}

	public MsgBean publish() {
		MsgBean msgbean = new MsgBean(message);
		String body = msgbean.createBody(splitkey);
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend(topicname, body);
		logger.info(msgbean.logMessageOut("publish"));
		return msgbean;
	}

	private InitialContext getInitialContext() throws NamingException {
		return new InitialContext();
	}

	public Session getConnection() throws Exception {
		ConnectionFactory cf = (ConnectionFactory) getInitialContext().lookup("jms/QueueConnectionFactory");
		conn = cf.createConnection();
		conn.start();
		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return session;
	}

	public Queue getQueue() throws Exception {
		return (Queue) getInitialContext().lookup("jms/ActiveMQueue");
	}

	public Topic getTopic() throws Exception {
		return (Topic) getInitialContext().lookup("jms/ActiveMQTopic");
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
			String body = buf.append(id).append(splitkey).append(message).toString();
			getConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage message = session.createTextMessage(body);
			Queue q = getQueue();
			session.createProducer(q).send(message);
			fullmsg = "Set id: " + id + ", msg: " + message;
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
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
			String body = buf.append(id).append(splitkey).append(message).toString();
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
