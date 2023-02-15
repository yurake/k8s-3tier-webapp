package webapp.tier.bean;

import webapp.tier.util.MsgUtils;

public class MsgBean {

	int id;
	String message;
	String fullmsg = "MsgBean init error";

	// Constructor
	public MsgBean() {
	}

	public MsgBean(int id, String message) {
		this.id = id;
		this.message = message;
	}

	/**
	* Class constructor specifying number of objects to create.
	*
	* @param id int number of id
	* @param message message
	* @param type message type that the message comes from
	*
	*/
	public MsgBean(int id, String message, String type) {
		this.id = id;
		this.message = message;
		setFullmsgIncImpl(type);
	}

	/**
	* Class constructor specifying number of objects to create.
	*
	* @param id String number of id
	* @param message message
	* @param type message type that the message comes from
	*
	*/
	public MsgBean(String id, String message, String type) {
		this.id = MsgUtils.stringToInt(id);
		this.message = message;
		setFullmsgIncImpl(type);
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
		setFullmsgIncImpl(type);
	}

	private void setFullmsgIncImpl(String type) {
		this.fullmsg = type + " : id: " + this.id + ", message: " + this.message;
	}

	public String getFullmsg() {
		return fullmsg;
	}
}
