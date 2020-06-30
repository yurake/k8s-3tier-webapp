package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

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
	}

	@Test
	void testPutMsgError() {
		try {
			Connection conn = new MockConnectionFactory().newConnection();
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
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = svc.getMsg(conn);
		assertThat(msgbean.getFullmsg(), containsString("No Data."));
	}

	@Test
	void testGetMsgWithData() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		svc.getMsg(conn);
		svc.putMsg(conn);
		MsgBean msgbean = svc.getMsg(conn);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
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
	void testPublishMsgError() {
		try {
			Connection conn = new MockConnectionFactory().newConnection();
			svc.publishMsg(conn);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}

	@Test
	void testIsActive() {
		assertThat(svc.isActive(), is(false));
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
