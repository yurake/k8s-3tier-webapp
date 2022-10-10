package webapp.tier;

import webapp.tier.cache.MemcachedService;

public class MemcachedControllerTest {

//	MemcachedController createMemcachedService() throws Exception {
//		MemcachedService svc = mock(MemcachedService.class);
//		when(svc.set()).thenReturn("OK");
//		when(svc.get()).thenReturn("OK");
//		return new MemcachedController() {
//			MemcachedService createMemcachedService() {
//				return svc;
//			}
//		};
//	}

	MemcachedController createMemcachedServiceNull() throws Exception {
		return new MemcachedController() {
			MemcachedService createMemcachedService() {
				return null;
			}
		};
	}

//	@Test
//	public void testcreateMemcachedService() {
//		try {
//			MemcachedController rsc = new MemcachedController();
//			assertThat(rsc.createMemcachedService(), is(instanceOf(MemcachedService.class)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testPutcache() {
//		try {
//			MemcachedController rsc = createMemcachedService();
//			Response resp = rsc.set();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testPutcacheError() {
//		try {
//			MemcachedController rsc = createMemcachedServiceNull();
//			Response resp = rsc.set();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testgetcache() {
//		try {
//			MemcachedController rsc = createMemcachedService();
//			Response resp = rsc.get();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testgetcacheError() {
//		try {
//			MemcachedController rsc = createMemcachedServiceNull();
//			Response resp = rsc.get();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
}
