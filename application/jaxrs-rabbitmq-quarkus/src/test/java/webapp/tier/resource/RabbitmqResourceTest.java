package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Connection;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.RabbitmqService;
import webapp.tier.util.CreateId;

@QuarkusTest
class RabbitmqResourceTest {

	@InjectMock
	RabbitmqService svc;

	@Test
	void testPut() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(svc.getConnection()).thenReturn(conn);
        when(svc.putMsg(conn)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/put")
				.then()
				.statusCode(200)
                .body(containsString("test"));
		conn.close();
	}

	@Test
	void testGet() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(svc.getConnection()).thenReturn(conn);
        when(svc.getMsg(conn)).thenReturn(msgbean);
		given()
				.when()
				.get("/quarkus/rabbitmq/get")
				.then()
				.statusCode(200)
                .body(containsString("test"));
		conn.close();
	}

	@Test
	void testPublish() throws Exception {
		Connection conn = new MockConnectionFactory().newConnection();
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(svc.getConnection()).thenReturn(conn);
        when(svc.publishMsg(conn)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(200)
                .body(containsString("test"));
		conn.close();
	}

}