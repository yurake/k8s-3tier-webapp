package webapp.tier;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.db.MysqlService;

public class MysqlControllerTest {

	private MysqlController createMysqlService() throws Exception {
		MysqlService svc = mock(MysqlService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(svc.select()).thenReturn(allmsg);
		when(svc.insert()).thenReturn("OK");
		when(svc.delete()).thenReturn("OK");
		return new MysqlController() {
			MysqlService createMysqlService() {
				return svc;
			}
		};
	}

	private MysqlController createMysqlServiceNull() throws Exception {
		return new MysqlController() {
			MysqlService createMysqlService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateMysqlService() {
		try {
			MysqlController rsc = new MysqlController();
			assertThat(rsc.createMysqlService(), is(instanceOf(MysqlService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

//	@Test
//	public void testinsert() {
//		try {
//			MysqlController rsc = createMysqlService();
//			Response resp = rsc.insert();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//	@Test
//	public void testinsertError() {
//		try {
//			MysqlController rsc = createMysqlServiceNull();
//			Response resp = rsc.insert();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testselect() {
//		try {
//			MysqlController rsc = createMysqlService();
//			Response resp = rsc.select();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("[OK]"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//	@Test
//	public void testselectError() {
//		try {
//			MysqlController rsc = createMysqlServiceNull();
//			Response resp = rsc.select();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

	@Test
	public void testdelete() {
		try {
			MysqlController rsc = createMysqlService();
			Response resp = rsc.delete();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testdeleteError() {
		try {
			MysqlController rsc = createMysqlServiceNull();
			Response resp = rsc.delete();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
