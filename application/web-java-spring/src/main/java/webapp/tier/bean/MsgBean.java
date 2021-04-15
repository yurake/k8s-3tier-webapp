package webapp.tier.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import webapp.tier.util.CreateId;

@Getter
@EqualsAndHashCode
@ToString
public class MsgBean {

	int id;
	String message = "MsgBean init error";

	public MsgBean() {
	}
	public MsgBean(String message) {
		this.id = CreateId.createid();
		this.message = message;
	}

	public MsgBean(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public MsgBean(String id, String message) {
		this.id = Integer.parseInt(id);
		this.message = message;
	}

	public String idtoString() {
		return String.valueOf(id);
	}

	public String createBody(String splitkey) {
		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		return buf.toString();
	}

	public String logMessageOut(String type) {
		return type + ": id: " + id + ", msg: " + message;
	}

	public String logMessageOut(String type, int id, String message) {
		return type + ": id: " + id + ", msg: " + message;
	}
}
