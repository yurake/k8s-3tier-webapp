package webapp.tier.service.subscribe;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.DeliverService;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqSubscriber extends DefaultConsumer{

	private static final Logger LOG = Logger.getLogger(RabbitmqSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

	public RabbitmqSubscriber(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			byte[] body) throws UnsupportedEncodingException {
		MsgBean msgbean = MsgUtils.splitBody(new String(body, StandardCharsets.UTF_8), splitkey);
		msgbean.setFullmsg("Received");
		LOG.log(Level.INFO, msgbean.getFullmsg());
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
		String response = deliversvc.random();
		LOG.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
