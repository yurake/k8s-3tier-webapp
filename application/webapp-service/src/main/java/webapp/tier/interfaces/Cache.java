package webapp.tier.interfaces;

import webapp.tier.bean.MsgBean;

public interface Cache {

	public MsgBean setMsg() throws Exception;

	public MsgBean getMsg() throws Exception;

}
