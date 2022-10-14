package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.datastax.oss.quarkus.test.CassandraTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
@QuarkusTestResource(CassandraTestResource.class)
class CassandraServiceTest {

	private static final String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Inject
	CassandraService svc;

	@AfterEach
	void afterEach() {
		svc.deleteMsg();
	}

	@Test
	void testInsertMsg() {
		try {
			MsgBean expected = svc.insertMsg();
			assertThat(expected.getFullmsg(), containsString(respbody));
			List<MsgBean> actual = svc.selectMsg();
			actual.forEach(s -> {
				assertThat(s.getFullmsg(), containsString(respbody));
				assertThat(s.getId(), is(expected.getId()));
			});
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSelectMsgList10() {
		try {
			List<Integer> expecteds = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				expecteds.add(svc.insertMsg().getId());
			}
			List<MsgBean> msgbeans = svc.selectMsg();
			msgbeans.forEach(s -> {
				System.out.println(s.getFullmsg());
				assertThat(s.getFullmsg(), containsString(respbody));
				assertThat(expecteds.contains(s.getId()), is(true));
			});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSelectMsgNoData() {
		try {
			List<MsgBean> msgbeans = svc.selectMsg();
			msgbeans.forEach(s -> {
				assertThat(s.getMessage(), is("No Data."));
				assertThat(s.getId(), is(0));
			});
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testDeleteMsgNoData() {
		try {
			String recv = svc.deleteMsg();
			assertThat(recv, is("Delete Msg Records"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testDeleteMsgWithData() {
		try {
			List<Integer> expecteds = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				expecteds.add(svc.insertMsg().getId());
			}
			String recv = svc.deleteMsg();
			assertThat(recv, is("Delete Msg Records"));
			List<MsgBean> msgbeans = svc.selectMsg();
			msgbeans.forEach(s -> {
				assertThat(s.getMessage(), is("No Data."));
				assertThat(s.getId(), is(0));
			});
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

}
