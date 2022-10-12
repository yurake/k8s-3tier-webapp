package webapp.tier.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.RabbitmqSocket;
import webapp.tier.util.MsgUtils;

public class RabbitmqConsumer extends DefaultConsumer {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key",
			String.class);
	RabbitmqSocket rmqsock = new RabbitmqSocket();

	public RabbitmqConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
			AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		long deliveryTag = envelope.getDeliveryTag();
		MsgBean msgbean2 = MsgUtils.splitBody(new String(body, StandardCharsets.UTF_8), splitkey);
		msgbean2.setFullmsg("Received");
		logger.log(Level.INFO, msgbean2.getFullmsg());
		rmqsock.onMessage(MsgUtils.createBody(msgbean2, splitkey));
		msgbean2.setFullmsg("Broadcast");
		logger.log(Level.INFO, msgbean2.getFullmsg());
		super.getChannel().basicAck(deliveryTag, false);
	}

}
