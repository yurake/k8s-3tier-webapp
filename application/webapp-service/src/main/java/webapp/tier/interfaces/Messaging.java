package webapp.tier.interfaces;

import webapp.tier.bean.MsgBean;

public interface Messaging {

	public MsgBean putMsg() throws Exception;

	public MsgBean getMsg() throws Exception;

	public MsgBean publishMsg() throws Exception;
}
