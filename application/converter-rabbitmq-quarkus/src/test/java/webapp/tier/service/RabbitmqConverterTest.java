package webapp.tier.service;

import static com.rabbitmq.client.BuiltinExchangeType.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Awaitility.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
class RabbitmqConverterTest {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String splitkey = ",";
	private static String queueName = "queuemsg";
	private static String routingkey = "routingkeymsg";
	private static String hostname = "localhost";
	private static String convertExchangeName = "converter";
	private static String messageExchangeName = "message";

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testConvert() throws Exception {
		Channel channel = getChannel();

		channel.exchangeDeclare(messageExchangeName, TOPIC, true, false, Map.of());
		channel.queueDeclare(queueName, true, false, false, Map.of());
		channel.queueBind(queueName, messageExchangeName, routingkey);

		AtomicReference<MsgBean> receivedMsg = new AtomicReference<>(null);

		String consumerTag = channel.basicConsume(queueName, false, "myConsumerTag",
				new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag,
							Envelope envelope,
							AMQP.BasicProperties properties,
							byte[] body)
							throws IOException {
						long deliveryTag = envelope.getDeliveryTag();
						MsgBean msgbean = MsgUtils.splitBody(new String(body, UTF_8),
								splitkey);
						msgbean.setFullmsg("Received Consumer");
						logger.log(Level.INFO, msgbean.getFullmsg());
						receivedMsg.set(msgbean);
						channel.basicAck(deliveryTag, false);
					}
				});

		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
				.contentType("text/plain")
				.build();
		channel.basicPublish(convertExchangeName, routingkey, props,
				body.getBytes(UTF_8));
		channel.basicPublish(convertExchangeName, routingkey, props,
				body.getBytes(UTF_8));

		await().atMost(10, SECONDS).untilAtomic(receivedMsg, notNullValue());

		channel.basicCancel(consumerTag);
	}

	Channel getChannel() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(hostname);
		connectionFactory.setChannelRpcTimeout((int) SECONDS.toMillis(3));
		return connectionFactory.newConnection().createChannel();
	}
}
