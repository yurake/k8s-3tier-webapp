package webapp.tier.service;

import webapp.tier.bean.MsgBean;

public final class RedisNotification {

	public final String key;
	public final MsgBean msgbean;

	public RedisNotification(String key, MsgBean msgbean) {
		this.key = key;
		this.msgbean = msgbean;
	}

}
