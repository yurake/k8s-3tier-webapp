package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Connection;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqSubscribeServiceTest {

	@Inject
	RabbitmqSubscribeService svc;

	@Test
	void testIsActiveFalse() {
		assertThat(svc.isActive(), is(false));
	}

	@Test
	void testIsActiveTrue() throws IOException, TimeoutException {
		RabbitmqSubscribeService rsvc = new RabbitmqSubscribeService() {
			public Connection getConnection() {
				Connection conn = new MockConnectionFactory().newConnection();
				return conn;
			}
		};
		assertThat(rsvc.isActive(), is(true));
	}

	@Test
	void testStartStopSubscribe() {
		try {
			RabbitmqSubscribeService.stopReceived();
			RabbitmqSubscribeService.startReceived();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testRun() throws IOException, TimeoutException {
		RabbitmqSubscribeService rsvc = new RabbitmqSubscribeService() {
			public Connection getConnection() {
				Connection conn = new MockConnectionFactory().newConnection();
				return conn;
			}
		};
		try {
			RabbitmqSubscribeService.stopReceived();
			rsvc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
