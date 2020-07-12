package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.HazelcastCacheService;
import webapp.tier.service.HazelcastMqService;
import webapp.tier.util.CreateId;

@QuarkusTest
class HazelcastResourceTest {

	@InjectMock
	HazelcastCacheService csvc;

	@InjectMock
	HazelcastMqService msvc;

	private static HazelcastInstance mockInstance;

	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}

	@Test
	void testPutcache() throws NoSuchAlgorithmException, RuntimeException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(csvc.createHazelcastInstance()).thenReturn(mockInstance);
        when(csvc.setMsg(mockInstance)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/setcache")
				.then()
				.statusCode(200);
	}

	@Test
	void testGetcache() throws NoSuchAlgorithmException {
		List<MsgBean> msgbeanList = new ArrayList<>();
		msgbeanList.add(new MsgBean(CreateId.createid(), "test", "Test"));
		msgbeanList.add(new MsgBean(CreateId.createid(), "test", "Test"));

        when(csvc.createHazelcastInstance()).thenReturn(mockInstance);
        when(csvc.getMsgList(mockInstance)).thenReturn(msgbeanList);
		given()
				.when()
				.get("/quarkus/hazelcast/getcache")
				.then()
				.statusCode(200);
	}

	@Test
	void testPutqueue() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(msvc.putMsg(mockInstance)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/putqueue")
				.then()
				.statusCode(200);
	}

	@Test
	void testGetqueue() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(msvc.getMsg(mockInstance)).thenReturn(msgbean);
		given()
				.when()
				.get("/quarkus/hazelcast/getqueue")
				.then()
				.statusCode(200);
	}

	@Test
	void testPublish() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(msvc.publishMsg(mockInstance)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/publish")
				.then()
				.statusCode(200);
	}

}

