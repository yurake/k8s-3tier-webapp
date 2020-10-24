package webapp.tier.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import com.datastax.oss.quarkus.test.CassandraTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.dao.MsgDao;

@QuarkusTest
@QuarkusTestResource(CassandraTestResource.class)
class CassandraServiceErrorTest {

	@Inject
	CassandraService svc;

	@InjectMock
	MsgDao dao;

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testInsertMsgError() {
		when(dao.update(ArgumentMatchers.any())).thenThrow(new RuntimeException());
		try {
			svc.insertMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getMessage(), is("Insert Error."));
		}
	}

	@Test
	void testSelectMsgError() {
		when(dao.findAll()).thenThrow(new RuntimeException());
		try {
			svc.selectMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getMessage(), is("Select Error."));
		}
	}

	@Test
	void testDeleteMsgError() {
		try {
			svc.deleteMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getMessage(), is("Delete Error."));
		}
	}

}
