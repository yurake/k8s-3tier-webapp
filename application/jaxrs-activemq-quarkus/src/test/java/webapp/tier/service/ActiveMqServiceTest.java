package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	static ThreadTestOnStartError th = new ThreadTestOnStartError();

	@BeforeAll
	static void threadTestOnStartError() {
		th.start();
	}

	@Test
	void testPutMsg() {

		ActiveMqService svc = new ActiveMqService();
		try {
			svc.putMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Put Error.", e.getMessage());
		}
	}

	@Test
	void testGetMsg() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.getMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testPublishMsg() {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.publishMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}

	static class ThreadTestOnStartError extends Thread {
		private final Logger log = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());
		@Override
		public void run() {
			ActiveMqService.stopReceived();
			log.log(Level.INFO, "Stopped Received");
		}
	}
}
