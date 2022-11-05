package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class RedisServiceTest {

	@Inject
	RedisService svc;

	@ConfigProperty(name = "common.message")
	String message;

	@Test
	void testPutMsg() {
		try {
			svc.delete();
			MsgBean bean = svc.putMsg();
			assertThat(bean.getFullmsg(), containsString(message));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testDeleteAndGetMsgListNoData() {
		svc.delete();
		List<MsgBean> msgbeans = svc.getMsgList();
		msgbeans.forEach(s -> {
			assertThat(s.getMessage(), is("No Data."));
			assertThat(s.getId(), is(0));
		});
	}

	@Test
	void testGetMsgList10() {
		try {
			svc.delete();
			List<Integer> expecteds = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				expecteds.add(svc.putMsg().getId());
			}
			List<MsgBean> msgbeans = svc.getMsgList();
			msgbeans.forEach(s -> {
				assertThat(s.getFullmsg(), containsString(message));
				assertThat(expecteds.contains(s.getId()), is(true));
			});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testPublish() {
		try {
			MsgBean msgbean = svc.publish();
			assertThat(msgbean.getFullmsg(), containsString(message));
			assertThat(msgbean.getFullmsg(), containsString("Publish"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}
}
