package org.acme;

import org.acme.service.RedisService;

public class TaskExecute implements Runnable{

	@Override
	public void run() {
		RedisService redissvc = new RedisService();
		redissvc.subscribeRedis();
	}

}
