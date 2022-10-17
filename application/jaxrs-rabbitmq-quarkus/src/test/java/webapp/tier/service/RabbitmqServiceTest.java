package webapp.tier.service;

import static com.rabbitmq.client.BuiltinExchangeType.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
class RabbitmqServiceTest {

	@Inject
	RabbitmqService svc;

	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String splitkey = ConfigProvider.getConfig()
			.getValue("rabbitmq.split.key", String.class);
	private static String queueName = ConfigProvider.getConfig()
			.getValue("mp.messaging.incoming.message.queue.name", String.class);
	private static String routingkey = ConfigProvider.getConfig()
			.getValue("mp.messaging.incoming.message.routing-keys", String.class);

	private static String hostname = "localhost";
	private static String exchangeName = "message";

	@Test
	void testPublishMsg() throws IOException {
		try {
			MsgBean msgbean = svc.publishMsg();
			assertThat(msgbean.getFullmsg(), containsString(message));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testConsume() throws Exception {

		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		Channel channel = getChannel();

		channel.exchangeDeclare(exchangeName, TOPIC, true, false, Map.of());
		channel.queueDeclare(queueName, true, false, false, Map.of());
		channel.queueBind(queueName, exchangeName, routingkey);
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
				.contentType("text/plain")
				.build();
		channel.basicPublish(exchangeName, routingkey, props, body.getBytes(UTF_8));
	}

	Channel getChannel() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(hostname);
		connectionFactory.setChannelRpcTimeout((int) SECONDS.toMillis(3));
		return connectionFactory.newConnection().createChannel();
	}

}
