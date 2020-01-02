package org.acme.events;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.hazelcast.core.Hazelcast;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class AppLifecycleBean {

	private static final Logger LOG = Logger.getLogger(AppLifecycleBean.class.getSimpleName());

    void onStart(@Observes StartupEvent ev) {
        TaskManager timer = TaskManager.getInstance();
        timer.exec();
        LOG.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
    	LOG.info("The application is stopping...");
    	Hazelcast.newHazelcastInstance().shutdown();
    }

}