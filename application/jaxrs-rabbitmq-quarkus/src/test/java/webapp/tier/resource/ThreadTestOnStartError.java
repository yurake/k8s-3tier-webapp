package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import webapp.tier.service.RabbitmqService;

class ThreadTestOnStartError extends Thread {

	private static final Logger LOG = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());

	@Override
	public void run() {
		RabbitmqService.stopReceived();
		LOG.log(Level.INFO, "Stopped Received");
	}
}
