package webapp.tier.service;

import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.util.CreateId;

public class MemcachedService {

	private static final Logger LOG = Logger.getLogger(MemcachedService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String serverconf = ConfigProvider.getConfig().getValue("memcached.server.conf", String.class);

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
		mcc.set("id", id);
		mcc.set("msg", message);

		fullmsg = "Set id: " + id + ", msg: " + message;
		LOG.info(fullmsg);
		return fullmsg;
	}

	public String getMemcached() {
		String fullmsg = null;
		MemCachedClient mcc = new MemCachedClient();

		String id = (String) mcc.get("id");
		String message = (String) mcc.get("msg");

		fullmsg = "Received id: " + id + ", msg: " + message;
		LOG.info(fullmsg);
		return fullmsg;
	}
}
