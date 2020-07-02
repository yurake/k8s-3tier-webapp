package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.fppt.jedismock.RedisServer;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import redis.clients.jedis.Jedis;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.RedisService;
import webapp.tier.util.CreateId;

@QuarkusTest
class RedisResourceTest {

	@InjectMock
	RedisService svc;

	private static RedisServer server = null;

	@BeforeAll
	public static void setup() throws IOException {
		server = RedisServer.newRedisServer();
		server.start();
	}

	@AfterAll
	public static void after() {
		server.stop();
		server = null;
	}

	private Jedis createJedisMock() {
		return new Jedis(server.getHost(), server.getBindPort());
	}

	@Test
	void testPut() throws NoSuchAlgorithmException {
		Jedis jedis = createJedisMock();
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Put");
        when(svc.createJedis()).thenReturn(jedis);
        when(svc.putMsg(jedis)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/put")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

	@Test
	void testGet() throws NoSuchAlgorithmException {
		Jedis jedis = createJedisMock();
		List<MsgBean> msglist = new ArrayList<>();
		msglist.add(new MsgBean(CreateId.createid(), "test", "Put"));
        when(svc.createJedis()).thenReturn(jedis);
        when(svc.getMsgList(jedis)).thenReturn(msglist);
		given()
				.when()
				.get("/quarkus/redis/get")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

	@Test
	void testPublish() throws NoSuchAlgorithmException {
		Jedis jedis = createJedisMock();
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Put");
        when(svc.createJedis()).thenReturn(jedis);
        when(svc.publishMsg(jedis)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/publish")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

}

