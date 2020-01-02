package org.acme.events;

import javax.enterprise.context.ApplicationScoped;

import org.acme.service.HazelcastService;

@ApplicationScoped
public class TaskExecute implements Runnable{

	@Override
	public void run() {
		HazelcastService redissvc = new HazelcastService();
		redissvc.subscribeHazelcast();
	}

}
