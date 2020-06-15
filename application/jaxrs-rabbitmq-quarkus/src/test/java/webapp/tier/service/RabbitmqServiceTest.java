package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqServiceTest {

	@Test
	void testPutMsgError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		RabbitmqService svc = new RabbitmqService();
		try {
			th.start();
			svc.putMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Put Error.", e.getMessage());
		}
	}

	@Test
	void testGetMsgError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		RabbitmqService svc = new RabbitmqService();
		try {
			th.start();
			svc.getMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Get Error.", e.getMessage());
		}
	}

	@Test
	void testPublishMsgError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		RabbitmqService svc = new RabbitmqService();
		try {
			th.start();
			svc.publishMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Publish Error.", e.getMessage());
		}
	}

}

class ThreadTestOnStartError extends Thread {

	private static final Logger LOG = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());

	@Override
	public void run() {
		RabbitmqService.stopReceived();
		LOG.log(Level.INFO, "Stopped Received");
	}
}
