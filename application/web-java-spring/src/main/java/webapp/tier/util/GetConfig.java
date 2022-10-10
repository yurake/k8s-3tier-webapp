package webapp.tier.util;

import java.util.ResourceBundle;

/**
 * propertiesの情報を取得する
 *
 * @author yura
 *
 */
public class GetConfig {
	private static final String PROPERTY = "java";

	private GetConfig() {
	}

	public static String getResourceBundle(String key) {
		return ResourceBundle.getBundle(PROPERTY).getString(key);
	}
}
