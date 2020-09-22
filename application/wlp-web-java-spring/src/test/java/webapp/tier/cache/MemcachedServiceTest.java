package webapp.tier.cache;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.whalin.MemCached.MemCachedClient;

class MemcachedServiceTest {

	@Test
	void testget() {
		Object obj = "11111111";
		String msg = "Hello k8s-3tier-webapp!";
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		Mockito.when(mcc.get("id")).thenReturn(obj.toString());
		Mockito.when(mcc.get("msg")).thenReturn(msg);
		MemcachedService svc = new MemcachedService() {
			public MemCachedClient createMemCachedClient() {
				return mcc;
			};
		};
		try {
			String result = svc.get();
			assertThat(result, is("Get id: 11111111, msg: Hello k8s-3tier-webapp!"));
		} catch (Exception expected) {
			expected.fillInStackTrace();
			fail();
		}
	}

	@Test
	void testgetError() {
		MemcachedService svc = new MemcachedService();
		try {
			svc.get();
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is("Failed get from Memcached"));
		}
	}

	@Test
	void testset() {
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		Mockito.when(mcc.set(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		MemcachedService svc = new MemcachedService() {
			public MemCachedClient createMemCachedClient() {
				return mcc;
			};
		};
		try {
			String result = svc.set();
			assertThat(result, containsString("Set id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception expected) {
			expected.fillInStackTrace();
			fail();
		}
	}

	@Test
	void testsetError() {
		MemcachedService svc = new MemcachedService();
		try {
			svc.set();
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is("Failed set to Memcached"));
		}
	}

}
