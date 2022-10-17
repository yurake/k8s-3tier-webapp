package webapp.tier.service;

import webapp.tier.bean.MsgBean;

public final class RedisDeliverNotification {

	public final String key;
	public final MsgBean msgbean;

	public RedisDeliverNotification(String key, MsgBean msgbean) {
		this.key = key;
		this.msgbean = msgbean;
	}

}
