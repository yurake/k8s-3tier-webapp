package webapp.tier.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MsgBean {

	int id;
	String message = "MsgBean init error";

	public MsgBean() {
	}

	public MsgBean(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public String logMessageOut(String type) {
		return type + ": id: " + id + ", msg: " + message;
	}

	public String logMessageOut(String type, int id, String message) {
		return type + ": id: " + id + ", msg: " + message;
	}
}
