package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testOnStartError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		ActiveMqService svc = new ActiveMqService();
		th.start();
		svc.run();
	}
}

class ThreadTestOnStartError extends Thread {

	private static final Logger LOG = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());

	@Override
	public void run() {
		ActiveMqService.stopReceived();
		LOG.log(Level.INFO, "Stopped Received");
	}
}
