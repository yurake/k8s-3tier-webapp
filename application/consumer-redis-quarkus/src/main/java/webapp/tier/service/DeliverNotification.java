package webapp.tier.service;

import webapp.tier.bean.MsgBean;

public final class DeliverNotification {

	public final String key;
	public final MsgBean msgbean;

	public DeliverNotification(String key, MsgBean msgbean) {
		this.key = key;
		this.msgbean = msgbean;
	}

}
