package org.acme.events;

import javax.enterprise.context.ApplicationScoped;

import org.acme.service.RedisService;

@ApplicationScoped
public class TaskExecute implements Runnable{

	@Override
	public void run() {
		RedisService redissvc = new RedisService();
		redissvc.subscribeRedis();
	}

}
