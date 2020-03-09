package webapp.tier.bean;

import java.io.Serializable;

public class MsgBean implements Serializable {

	int id;
	String message;

	public MsgBean() {}

	public MsgBean(int id, String message) {
		setId(id);
		setMessage(message);
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
}
