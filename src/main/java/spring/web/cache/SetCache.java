package spring.web.cache;

import javax.servlet.http.HttpServlet;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class SetCache extends HttpServlet {
	private static String serverconf = GetConfig.getResourceBundle("cache.server.conf");
	private static String message = GetConfig.getResourceBundle("common.message");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public String setCache() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		MemcachedClient mcc = new MemcachedClient();
		try {
			mcc.set("id", id);
			mcc.set("msg", message);

			fullmsg = "Set id: " + id + ", msg: " + message;
			System.out.println(fullmsg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fullmsg;
	}
}
