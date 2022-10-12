package webapp.tier.service;

import webapp.tier.bean.MsgBean;

public final class Notification {

	public final String key;
	public final MsgBean msgbean;

	public Notification(String key, MsgBean msgbean) {
		this.key = key;
		this.msgbean = msgbean;
	}

}
