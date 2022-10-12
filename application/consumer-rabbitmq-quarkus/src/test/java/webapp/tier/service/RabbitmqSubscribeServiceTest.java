package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.github.fridujo.rabbitmq.mock.MockConnection;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqSubscribeServiceTest {

	@Inject
	RabbitmqSubscribeService svc;

	private static final String exchangename = "exchangemsg";
	private static final String routingkey = "routingkeymsg";

	private static MockConnection createRabbitmqMock() {
		MockConnection conn = new MockConnectionFactory().newConnection();
		return conn;
	}

	@Test
	void testSubscribe() {
		try (MockConnection conn = createRabbitmqMock();
				Channel channel = conn.createChannel()) {
			RabbitmqSubscribeService rsvc = new RabbitmqSubscribeService() {
				public Connection getConnection() {
					return conn;
				}
			};
			Thread thread = new Thread(rsvc);
			thread.start();

			channel.exchangeDeclare(exchangename, "direct", true);
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchangename, routingkey);

			channel.exchangeDeclare(exchangename, "direct", true);
			channel.basicPublish(exchangename, routingkey, null, "0000,Test".getBytes(StandardCharsets.UTF_8));
			TimeUnit.MILLISECONDS.sleep(200L);
			channel.basicPublish(exchangename, routingkey, null, "1111,Test".getBytes(StandardCharsets.UTF_8));
			TimeUnit.MILLISECONDS.sleep(200L);
			channel.basicPublish(exchangename, routingkey, null, "2222,Test".getBytes(StandardCharsets.UTF_8));
			TimeUnit.MILLISECONDS.sleep(200L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSubscribeNoError() {
		Connection conn = mock(Connection.class);
		Channel chan = mock(Channel.class);
		try {
			when(conn.createChannel()).thenReturn(chan);
			RabbitmqSubscribeService rsvc = new RabbitmqSubscribeService() {
				public Connection getConnection() {
					return conn;
				}
			};
			rsvc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
