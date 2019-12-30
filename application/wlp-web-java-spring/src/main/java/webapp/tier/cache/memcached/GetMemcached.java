package webapp.tier.cache.memcached;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.util.GetConfig;

public class GetMemcached extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(GetMemcached.class);
	private static String serverconf = GetConfig.getResourceBundle("memcached.server.conf");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public String getMemcached() {
		String fullmsg = null;
		MemCachedClient mcc = new MemCachedClient();

		String id = (String) mcc.get("id");
		String message = (String) mcc.get("msg");

		fullmsg = "Received id: " + id + ", msg: " + message;
		logger.info(fullmsg);
		return fullmsg;
	}
}
