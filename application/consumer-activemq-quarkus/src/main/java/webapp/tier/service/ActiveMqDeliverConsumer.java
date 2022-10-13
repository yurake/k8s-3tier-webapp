package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class ActiveMqDeliverConsumer {

	@ConfigProperty(name = "activemq.split.key")
	String splitkey;

	@Inject
	@RestClient
	ActiveMqDeliverService deliversvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public void consume(JMSConsumer consumer) throws JMSException {
		logger.log(Level.INFO, "Ready for receive message...");
		Message message = consumer.receive();
		TextMessage textMessage = (TextMessage) message;
		MsgBean msgbean = MsgUtils.splitBody(textMessage.getText(), splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
