package webapp.tier.bean;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LatestMessage {

	private LatestMessage() {
	}

	private static String latestMsg = "No Data.";

	public static String getLatestMsg() {
		return latestMsg;
	}

	public static void setLatestMsg(String latestMsg) {
		LatestMessage.latestMsg = latestMsg;
	}

}
