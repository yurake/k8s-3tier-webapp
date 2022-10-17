package webapp.tier.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
import webapp.tier.db.MysqlService;

public class MysqlResourceTest {

	private MysqlResource createMysqlService() throws Exception {
		MysqlService svc = mock(MysqlService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(svc.select()).thenReturn(allmsg);
		when(svc.insert()).thenReturn("OK");
		when(svc.delete()).thenReturn("OK");
		return new MysqlResource() {
			MysqlService createMysqlService() {
				return svc;
			}
		};
	}

	private MysqlResource createMysqlServiceNull() throws Exception {
		return new MysqlResource() {
			MysqlService createMysqlService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateMysqlService() {
		try {
			MysqlResource rsc = new MysqlResource();
			assertThat(rsc.createMysqlService(), is(instanceOf(MysqlService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testinsert() {
		try {
			MysqlResource rsc = createMysqlService();
			Response resp = rsc.mysinsert();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testinsertError() {
		try {
			MysqlResource rsc = createMysqlServiceNull();
			Response resp = rsc.mysinsert();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testselect() {
		try {
			MysqlResource rsc = createMysqlService();
			Response resp = rsc.mysselect();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("[OK]"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testselectError() {
		try {
			MysqlResource rsc = createMysqlServiceNull();
			Response resp = rsc.mysselect();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testdelete() {
		try {
			MysqlResource rsc = createMysqlService();
			Response resp = rsc.mysdelete();
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
			MysqlResource rsc = createMysqlServiceNull();
			Response resp = rsc.mysdelete();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
