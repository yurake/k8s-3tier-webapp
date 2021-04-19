package webapp.tier.mq;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import webapp.tier.bean.MsgBean;

@Service
public class ActiveMqService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${common.message}")
	private String message;

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
}
