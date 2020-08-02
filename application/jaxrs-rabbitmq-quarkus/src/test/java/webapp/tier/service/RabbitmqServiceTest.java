package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.github.fridujo.rabbitmq.mock.MockConnection;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class RabbitmqServiceTest {

	@Inject
	RabbitmqService svc;

	private static final String respbody = "message: Hello k8s-3tier-webapp with quarkus";
	private static final String errormsg = "channel is already closed due to clean channel shutdown";
	private static final String exchangename = "exchangemsg";
	private static final String routingkey = "routingkeymsg";

	private static MockConnection createRabbitmqMock() {
		MockConnection conn = new MockConnectionFactory().newConnection();
		return conn;
	}

	@Test
	void testPutMsg() throws Exception {
		try (Connection conn = createRabbitmqMock()) {
			MsgBean msgbean = svc.putMsg(conn);
			assertThat(msgbean.getFullmsg(), containsString(respbody));
			conn.close();
		}
	}

	@Test
	void testPutMsgError() throws IOException {
		try (Connection conn = createRabbitmqMock()) {
			conn.close();
			svc.putMsg(conn);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(errormsg, e.getMessage());
		}
	}

	@Test
	void testGetMsgNoData() throws Exception {
		try (Connection conn = createRabbitmqMock()) {
			MsgBean msgbean = svc.getMsg(conn);
			assertThat(msgbean.getFullmsg(), containsString("No Data."));
		}
	}

	@Test
	void testGetMsgWithData() throws Exception {
		try (Connection conn = createRabbitmqMock()) {
			svc.getMsg(conn);
			svc.putMsg(conn);
			MsgBean msgbean = svc.getMsg(conn);
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		}
	}

	@Test
	void testGetMsgError() {
		try (Connection conn = createRabbitmqMock()) {
			conn.close();
			svc.getMsg(conn);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(errormsg, e.getMessage());
		}
	}

	@Test
	void testPublishMsgError() throws IOException {
		try (Connection conn = createRabbitmqMock()) {
			svc.publishMsg(conn);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}

	@Test
	void testIsActiveFalse() {
		assertThat(svc.isActive(), is(false));
	}

	@Test
	void testIsActiveTrue() throws IOException, TimeoutException {
		try (MockConnection conn = createRabbitmqMock()) {
			RabbitmqService rsvc = new RabbitmqService() {
				public Connection getConnection() {
					return conn;
				}
			};
			assertThat(rsvc.isActive(), is(true));
		}
	}

	@Test
	void testSubscribe() {

		try (MockConnection conn = createRabbitmqMock();
				Channel channel = conn.createChannel()) {
			RabbitmqService rsvc = new RabbitmqService() {
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
	void testIsEnableReceived() {

		try (MockConnection conn = createRabbitmqMock();
				Channel channel = conn.createChannel()) {
			RabbitmqService rsvc = new RabbitmqService() {
				public Connection getConnection() {
					return conn;
				}
			};
			Thread thread = new Thread(rsvc);
			RabbitmqService.stopReceived();
			thread.start();
			RabbitmqService.startReceived();

			channel.exchangeDeclare(exchangename, "direct", true);
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchangename, routingkey);

			channel.exchangeDeclare(exchangename, "direct", true);
			channel.basicPublish(exchangename, routingkey, null, "0000,Test".getBytes(StandardCharsets.UTF_8));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
