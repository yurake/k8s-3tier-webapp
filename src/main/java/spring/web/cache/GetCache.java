package spring.web.cache;

import javax.servlet.http.HttpServlet;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import spring.web.util.GetConfig;

public class GetCache extends HttpServlet {
	private static String serverconf = GetConfig.getResourceBundle("cache.server.conf");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public String getCache() {
		String fullmsg = null;
		MemcachedClient mcc = new MemcachedClient();

		try {
			String id = (String) mcc.get("id");
			String message = (String) mcc.get("msg");

			fullmsg = "Received id: " + id + ", msg: " + message;
			System.out.println(fullmsg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fullmsg;
	}
}
