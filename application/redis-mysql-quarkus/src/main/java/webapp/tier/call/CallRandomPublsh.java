package webapp.tier.call;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.service.DeliverService;

@ApplicationScoped
public class CallRandomPublsh {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(CallRandomPublsh.class.getSimpleName());

	public void callRandomPublsh() {
		String response;
		LOG.info("Call: Random Publish");
		response = deliversvc.random();
		LOG.info(response);
	}
}
