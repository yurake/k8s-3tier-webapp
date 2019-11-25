package org.acme.events;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class AppLifecycleBean {

    void onStart(@Observes StartupEvent ev) {
        TaskManager timer = TaskManager.getInstance();
        timer.exec();
        System.out.println("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
    	System.out.println("The application is stopping...");
    }

}