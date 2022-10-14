package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.whalin.MemCached.MemCachedClient;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class MemcachedServiceTest {

	@Inject
	MemcachedService svcMock;

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testSetMemcached() {
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		Mockito.when(mcc.set(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		try {
			MsgBean msgbean = svcMock.setMsg(mcc);
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		} catch (Exception expected) {
			expected.fillInStackTrace();
			fail();
		}
	}

	@Test
	void testSetMemcachedError() {
		MemcachedService svc = new MemcachedService();
		try {
			svc.setMsg(svc.createMemCachedClient());
			fail();
		} catch (Exception expected) {
			assertEquals("Set Error.", expected.getMessage());
		}
	}

	@Test
	void testGetMemcached() {
		Object obj = "11111111";
		String msg = "Hello k8s-3tier-webapp with quarkus";
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		Mockito.when(mcc.get("id")).thenReturn(obj.toString());
		Mockito.when(mcc.get("msg")).thenReturn(msg);

		MsgBean bean = svcMock.getMsg(mcc);
		assertThat(bean.getId(), is(Integer.valueOf(obj.toString())));
		assertThat(bean.getMessage(), is(msg));
	}

	@Test
	void testGetMemcachedNoData() {
		MemcachedService svc = new MemcachedService();
		try {
			assertEquals("No Data.",
					svc.getMsg(svc.createMemCachedClient()).getMessage());
		} catch (Exception expected) {
			fail();
		}
	}

	@Test
	void testGetMemcachedNullError() {
		MemCachedClient mcc = null;
		try {
			svcMock.getMsg(mcc);
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is("Get Error."));
		}
	}
}
