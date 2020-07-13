package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.whalin.MemCached.MemCachedClient;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.MemcachedService;
import webapp.tier.util.CreateId;

@QuarkusTest
class MemcachedResourceTest {

	@InjectMock
	MemcachedService svc;

	@Test
	void testSet() throws NoSuchAlgorithmException, RuntimeException {
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(svc.createMemCachedClient()).thenReturn(mcc);
        when(svc.setMsg(mcc)).thenReturn(msgbean);
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/memcached/set")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

	@Test
	void testGet() throws NoSuchAlgorithmException {
		MemCachedClient mcc = Mockito.mock(MemCachedClient.class);
		MsgBean msgbean = new MsgBean(CreateId.createid(), "test", "Test");
        when(svc.createMemCachedClient()).thenReturn(mcc);
        when(svc.getMsg(mcc)).thenReturn(msgbean);
		given()
				.when()
				.get("/quarkus/memcached/get")
				.then()
				.statusCode(200)
                .body(containsString("test"));
	}

}

