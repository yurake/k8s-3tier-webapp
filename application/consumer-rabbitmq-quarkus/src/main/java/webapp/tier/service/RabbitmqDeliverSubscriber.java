package webapp.tier.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

public class RabbitmqDeliverSubscriber extends RabbitmqConsumer {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);

	public RabbitmqDeliverSubscriber(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		long deliveryTag = envelope.getDeliveryTag();
		MsgBean msgbean = MsgUtils.splitBody(new String(body, StandardCharsets.UTF_8), splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
		super.getChannel().basicAck(deliveryTag, false);
	}
}
