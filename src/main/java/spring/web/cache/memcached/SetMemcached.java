package spring.web.cache.memcached;

import javax.servlet.http.HttpServlet;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class SetMemcached extends HttpServlet {
	private static String serverconf = GetConfig.getResourceBundle("memcached.server.conf");
	private static String message = GetConfig.getResourceBundle("common.message");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public String setMemcached() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		MemcachedClient mcc = new MemcachedClient();
		try {
			mcc.set("id", id);
			mcc.set("msg", message);

			fullmsg = "Set id: " + id + ", msg: " + message;
			System.out.println(fullmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullmsg;
	}
}
