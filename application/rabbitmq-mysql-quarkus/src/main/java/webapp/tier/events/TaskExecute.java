package webapp.tier.events;

import javax.enterprise.context.ApplicationScoped;

import webapp.tier.service.RedisService;

@ApplicationScoped
public class TaskExecute implements Runnable{

	@Override
	public void run() {
		RedisService redissvc = new RedisService();
		redissvc.subscribeRedistoMysql();
	}

}
