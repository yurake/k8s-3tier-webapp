package webapp.tier.cache.memcached;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class SetMemcached extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(SetMemcached.class);
	private static String serverconf = GetConfig.getResourceBundle("memcached.server.conf");
	private static String message = GetConfig.getResourceBundle("common.message");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public String setMemcached() throws Exception {
		String fullmsg = "Error";
		String id = String.valueOf(CreateId.createid());
		boolean resultid = false;
		boolean resultmsg = false;

		MemCachedClient mcc = new MemCachedClient();
		resultid = mcc.set("id", id);
		resultmsg = mcc.set("msg", message);

		if (resultid && resultmsg) {
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} else {
			fullmsg = "Failed set to Memcached";
			logger.warn(fullmsg);
			throw new RuntimeException(fullmsg);
		}
		return fullmsg;
	}
}
