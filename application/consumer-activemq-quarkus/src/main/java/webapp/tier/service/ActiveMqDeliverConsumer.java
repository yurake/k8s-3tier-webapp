package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class ActiveMqDeliverConsumer extends ActiveMqConsumer {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(ActiveMqDeliverConsumer.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.split.key", String.class);

	@Override
	public void consume(JMSConsumer consumer) throws JMSException {
		LOG.log(Level.INFO, "Ready for receive message...");
		Message message = consumer.receive();
		TextMessage textMessage = (TextMessage) message;
		MsgBean msgbean = MsgUtils.splitBody(textMessage.getText(), splitkey);
		msgbean.setFullmsg("Received");
		LOG.log(Level.INFO, msgbean.getFullmsg());
		String response = deliversvc.random();
		LOG.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
