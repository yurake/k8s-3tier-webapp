package webapp.tier.service;

import static com.rabbitmq.client.BuiltinExchangeType.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.concurrent.TimeUnit.*;

import java.util.Map;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
class RabbitmqSubscribeServiceTest {

	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String splitkey = ConfigProvider.getConfig()
			.getValue("rabbitmq.split.key", String.class);
	private static String queueName = ConfigProvider.getConfig()
			.getValue("mp.messaging.incoming.message.queue.name", String.class);
	private static String routingkey = ConfigProvider.getConfig()
			.getValue("mp.messaging.incoming.message.routing-keys", String.class);
	private static String hostname = "localhost";
	private static String exchangeName = "exchangemsg";

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testSubscribe() throws Exception {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		Channel channel = getChannel();

		channel.exchangeDeclare(exchangeName, TOPIC, true, false, Map.of());
		channel.queueDeclare(queueName, true, false, false, Map.of());
		channel.queueBind(queueName, exchangeName, routingkey);
		/**
		AtomicReference<String> receivedMessage = new AtomicReference<>(null);
		DeliverCallback deliverCallback = (consumerTag, message) -> {
			String subscribedbody = objectMapper.readValue(message.getBody(),
					String.class);
			if (!Objects.equals(subscribedbody, body)) {
				return;
			}
			receivedMessage.set(subscribedbody);
		};
		String consumerTag = channel.basicConsume(queueName, true, deliverCallback, tag -> {
		});
		
		DefaultConsumer consumer = new DefaultConsumer(channel);
		String consumerTag = channel.basicConsume(queueName, true, "myConsumerTag", consumer);
		**/

		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
				.contentType("text/plain")
				.build();
		channel.basicPublish(exchangeName, routingkey, props, body.getBytes(UTF_8));

		/**
		await().atMost(3, SECONDS).untilAtomic(receivedMessage, notNullValue());
		channel.basicCancel(consumerTag);
		**/
	}

	Channel getChannel() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(hostname);
		connectionFactory.setChannelRpcTimeout((int) SECONDS.toMillis(3));
		return connectionFactory.newConnection().createChannel();
	}
}
