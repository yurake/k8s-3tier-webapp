package io.helidon.events;

import javax.enterprise.context.ApplicationScoped;

import io.helidon.service.RedisService;

@ApplicationScoped
public class TaskExecute implements Runnable{

	@Override
	public void run() {
		RedisService redissvc = new RedisService();
		redissvc.subscribeRedis();
	}

}
