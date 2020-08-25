package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HazelcastSubscribeService extends HazelcastMqService {

	protected HazelcastDeliverSubscriber createHazelcastDeliverSubscriber() {
		return new HazelcastDeliverSubscriber();
	}

	@Override
	public void run() {
		subscribeHazelcast(HazelcastService.getInstance(), createHazelcastDeliverSubscriber());
	}
}
