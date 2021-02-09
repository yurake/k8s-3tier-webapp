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

import webapp.tier.PostgresResource;
import webapp.tier.db.PostgresService;

public class PostgresControllerTest {

	private PostgresResource createPostgresService() throws Exception {
		PostgresService svc = mock(PostgresService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(svc.select()).thenReturn(allmsg);
		when(svc.insert()).thenReturn("OK");
		when(svc.delete()).thenReturn("OK");
		return new PostgresResource() {
			PostgresService createPostgresService() {
				return svc;
			}
		};
	}

	private PostgresResource createPostgresServiceNull() throws Exception {
		return new PostgresResource() {
			PostgresService createPostgresService() {
				return null;
			}
		};
	}

	@Test
	public void testcreatePostgresService() {
		try {
			PostgresResource rsc = new PostgresResource();
			assertThat(rsc.createPostgresService(), is(instanceOf(PostgresService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testinsert() {
		try {
			PostgresResource rsc = createPostgresService();
			Response resp = rsc.insert();
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
			PostgresResource rsc = createPostgresServiceNull();
			Response resp = rsc.insert();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testselect() {
		try {
			PostgresResource rsc = createPostgresService();
			Response resp = rsc.select();
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
			PostgresResource rsc = createPostgresServiceNull();
			Response resp = rsc.select();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testdelete() {
		try {
			PostgresResource rsc = createPostgresService();
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
			PostgresResource rsc = createPostgresServiceNull();
			Response resp = rsc.delete();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
