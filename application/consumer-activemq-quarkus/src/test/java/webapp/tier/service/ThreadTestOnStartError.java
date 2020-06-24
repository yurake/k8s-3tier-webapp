package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadTestOnStartError extends Thread {

	private static final Logger LOG = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());

	@Override
	public void run() {
		ActiveMqService.stopReceived();
		LOG.log(Level.INFO, "Stopped Received");
	}
}
