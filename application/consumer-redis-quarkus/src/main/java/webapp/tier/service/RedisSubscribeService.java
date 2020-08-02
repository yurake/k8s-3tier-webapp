package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisSubscribeService extends RedisService {

	protected RedisSubscriber createRedisDeliverSubscriber() {
		return new RedisDeliverSubscriber();
	}

	@Override
	public void run() {
		subscribeRedis(createRedisDeliverSubscriber());
	}

}
