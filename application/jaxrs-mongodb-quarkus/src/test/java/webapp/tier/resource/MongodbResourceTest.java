package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.MongodbService;
import webapp.tier.util.CreateId;

@QuarkusTest
class MongodbResourceTest {

	@InjectMock
	MongodbService svc;

	@Test
	void testInsert() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
		when(svc.insertMsg(ArgumentMatchers.any())).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/insert")
				.then()
				.statusCode(200)
				.body(containsString("test"));
	}

	@Test
	void testSelect() throws NoSuchAlgorithmException {
		List<MsgBean> msglist = new ArrayList<>();
		msglist.add(new MsgBean(CreateId.createid(), "test", "Test"));
		when(svc.selectMsg(ArgumentMatchers.any())).thenReturn(msglist);
		given()
				.when()
				.get("/quarkus/mongodb/select")
				.then()
				.statusCode(200)
				.body(containsString("test"));
	}

	@Test
	void testDelete() {
        when(svc.deleteMsg(ArgumentMatchers.any())).thenReturn("test");
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/delete")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

}
