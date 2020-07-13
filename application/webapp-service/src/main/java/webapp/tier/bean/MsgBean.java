package webapp.tier.bean;

import webapp.tier.util.MsgUtils;

public class MsgBean {

	int id;
	String message;
	String fullmsg = "MsgBean init error";

	public MsgBean() {
	}

	public MsgBean(int id, String message) {
		setId(id);
		setMessage(message);
	}

	public MsgBean(int id, String message, String type) {
		setId(id);
		setMessage(message);
		setFullmsg(type);
	}

	public MsgBean(String id, String message, String type) {
		setId(MsgUtils.stringToInt(id));
		setMessage(message);
		setFullmsg(type);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFullmsg(String type) {
		this.fullmsg = type + " : id: " + getId() + ", message: " + getMessage();
	}

	public String getFullmsg() {
		return fullmsg;
	}
}
