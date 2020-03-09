package webapp.tier.util;

import java.util.Objects;

import webapp.tier.bean.MsgBean;

public class MsgBeanUtils extends MsgBean {

	String fullmsg = "Error";

	public MsgBeanUtils() {
	};

	public MsgBeanUtils(int id, String message) {
		setId(id);
		setMessage(message);
	};

	public void setFullmsg(String fullmsg) {
		this.fullmsg = fullmsg;
	}

	public void setFullmsgWithType(MsgBean msgbean, String type) {
		this.fullmsg = type + " id: " + msgbean.getId() + ", message: " + msgbean.getMessage();
	}

	public String getFullmsg() {
		return fullmsg;
	}

	public void setIdString(String id) {
		setId(Integer.parseInt(id));
	}

	public String getIdString() {
		return String.valueOf(getId());
	}

	public MsgBean appendMessage(MsgBean bean, String appendmessage) {
		StringBuilder builder = new StringBuilder();
		builder.append(bean.getMessage());
		builder.append(" ");
		builder.append(appendmessage);
		bean.setMessage(builder.toString());
		return bean;
	}

	public String createBody(MsgBean bean, String splitkey) {
		StringBuilder buf = new StringBuilder();
		buf.append(bean.getId());
		buf.append(splitkey);
		buf.append(bean.getMessage());
		return buf.toString();
	}

	public MsgBean splitBody(String jmsbody, String splitkey) {
		String[] body = jmsbody.split(splitkey, 0);
		return new MsgBean(Integer.parseInt(body[0]), body[1]);
	}

	public boolean checkMsgBeanUtils(MsgBeanUtils msgbean) {
		return (msgbean.getId() == 0 || Objects.isNull(msgbean.getMessage()));
	}
}
