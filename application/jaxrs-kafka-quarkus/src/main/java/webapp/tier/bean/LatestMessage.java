package webapp.tier.bean;

public class LatestMessage {

	private static String latestMsg;

	public static String getLatestMsg() {
		return latestMsg;
	}

	public static void setLatestMsg(String latestMsg) {
		LatestMessage.latestMsg = latestMsg;
	}

}
