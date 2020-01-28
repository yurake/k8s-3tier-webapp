package webapp.tier.events;

import javax.enterprise.context.ApplicationScoped;

import webapp.tier.service.HazelcastService;

@ApplicationScoped
public class TaskExecute implements Runnable{

	@Override
	public void run() {
		HazelcastService redissvc = new HazelcastService();
		redissvc.subscribeHazelcast();
	}

}
