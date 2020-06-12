package webapp.tier.events;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.service.HazelcastMqService;

@ApplicationScoped
public class AppLifecycleBean {

	private static final Logger LOG = Logger.getLogger(AppLifecycleBean.class.getSimpleName());

	@Inject
	HazelcastMqService hazsvc;

    void onStart(@Observes StartupEvent ev) {
    	hazsvc.init();
    	LOG.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
    	LOG.info("The application is stopping...");
    }

}