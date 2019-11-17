package spring.web.cache.memcached;

import javax.servlet.http.HttpServlet;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

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

		MemCachedClient mcc = new MemCachedClient();
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
