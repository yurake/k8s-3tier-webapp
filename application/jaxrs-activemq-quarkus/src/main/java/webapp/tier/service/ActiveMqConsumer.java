package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.ActiveMqSocket;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class ActiveMqConsumer {

	private static final Logger LOG = Logger.getLogger(ActiveMqConsumer.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.split.key", String.class);
	ActiveMqSocket amqsock = new ActiveMqSocket();

	public void consume(JMSConsumer consumer) throws JMSException {
		LOG.log(Level.INFO, "Ready for receive message...");
		Message message = consumer.receive();
		TextMessage textMessage = (TextMessage) message;
		MsgBean msgbean = MsgUtils.splitBody(textMessage.getText(), splitkey);
		msgbean.setFullmsg("Received");
		LOG.log(Level.INFO, msgbean.getFullmsg());
		amqsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
		msgbean.setFullmsg("Broadcast");
		LOG.log(Level.INFO, msgbean.getFullmsg());
	}
}
