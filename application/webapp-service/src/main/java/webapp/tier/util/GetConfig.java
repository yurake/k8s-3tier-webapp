package webapp.tier.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * propertiesの情報を取得する
 *
 * @author yurak
 *
 */
public class GetConfig {
	private static Logger logger = LoggerFactory.getLogger(GetConfig.class);
	private static final String PROPERTY_FILE = "java.properties";
	private static final Properties properties;

	static {
		properties = new Properties();
		try {
			InputStream istream = new FileInputStream(PROPERTY_FILE);
			properties.load(istream);
		} catch (IOException e) {
			System.out.println(String.format("Faild load propertiy file: ", PROPERTY_FILE));
		}
	}

	public static String getResourceBundle(String key) {
		logger.info("Search: " + key);
		String result = properties.getProperty(key);
		logger.info("Result: " + result);
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}
}
