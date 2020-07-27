package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Connection;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class RabbitmqServiceTest {

	@Inject
	RabbitmqService svc;

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";
	String errormsg = "channel is already closed due to clean channel shutdown";

	@Test
	void testPutMsg() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = svc.putMsg(conn);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
		conn.close();
	}

	@Test
	void testPutMsgError() throws IOException {
		Connection conn = null;
		try {
			conn = new MockConnectionFactory().newConnection();
			conn.close();
			svc.putMsg(conn);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(errormsg, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Test
	void testGetMsgNoData() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = svc.getMsg(conn);
		assertThat(msgbean.getFullmsg(), containsString("No Data."));
		conn.close();
	}

	@Test
	void testGetMsgWithData() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		svc.getMsg(conn);
		svc.putMsg(conn);
		MsgBean msgbean = svc.getMsg(conn);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
		conn.close();
	}

	@Test
	void testGetMsgError() {
		try {
			Connection conn = new MockConnectionFactory().newConnection();
			conn.close();
			svc.getMsg(conn);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(errormsg, e.getMessage());
		}
	}

	@Test
	void testPublishMsgError() throws IOException {
		Connection conn = null;
		try {
			conn = new MockConnectionFactory().newConnection();
			svc.publishMsg(conn);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Test
	void testIsActiveFalse() {
		assertThat(svc.isActive(), is(false));
	}

	@Test
	void testIsActiveTrue() throws IOException, TimeoutException {
		RabbitmqService rsvc = new RabbitmqService() {
			public Connection getConnection() {
				Connection conn = new MockConnectionFactory().newConnection();
				return conn;
			}
		};
		assertThat(rsvc.isActive(), is(true));
	}

	@Test
	void testStartStopSubscribe() {

		try {
			RabbitmqService.stopReceived();
			RabbitmqService.startReceived();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
