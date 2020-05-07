package webapp.tier.util;

import java.util.Objects;

import webapp.tier.bean.MsgBean;

public class MsgUtils {

	MsgUtils() {
	};

	public static int stringToInt(String id) {
		return Integer.parseInt(id);
	}

	public static String intToString(int id) {
		return String.valueOf(id);
	}

	public static MsgBean appendMessage(MsgBean bean, String appendmessage) {
		StringBuilder builder = new StringBuilder();
		builder.append(bean.getMessage());
		builder.append(" ");
		builder.append(appendmessage);
		bean.setMessage(builder.toString());
		return bean;
	}

	public static String createBody(MsgBean bean, String splitkey) {
		StringBuilder buf = new StringBuilder();
		buf.append(bean.getId());
		buf.append(splitkey);
		buf.append(bean.getMessage());
		return buf.toString();
	}

	public static MsgBean splitBody(String body, String splitkey) {
		String[] arraybody = body.split(splitkey, 0);
		return new MsgBean(Integer.parseInt(arraybody[0]), arraybody[1]);
	}

	public static boolean checkMsgBeanUtils(MsgBean msgbean) {
		return (msgbean.getId() == 0 || Objects.isNull(msgbean.getMessage()));
	}
}
