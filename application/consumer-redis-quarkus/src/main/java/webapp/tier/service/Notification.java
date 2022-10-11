package webapp.tier.service;

import webapp.tier.bean.MsgBean;

public final class Notification {

	public String key;
	public MsgBean msgbean;

	public Notification() {
	}
	
	public Notification(String key, MsgBean msgbean) {
		this.key = key;
		this.msgbean = msgbean;
	}
	
}
