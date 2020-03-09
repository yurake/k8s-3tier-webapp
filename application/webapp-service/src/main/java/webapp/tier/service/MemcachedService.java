package webapp.tier.service;

import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.interfaces.Cache;
import webapp.tier.util.CreateId;

public class MemcachedService implements Cache{

	private static final Logger LOG = Logger.getLogger(MemcachedService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String serverconf = ConfigProvider.getConfig().getValue("memcached.server.conf", String.class);

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	@Override
	public String setMsg() throws Exception {
		String fullmsg = "Error";
		String id = String.valueOf(CreateId.createid());
		boolean resultid = false;
		boolean resultmsg = false;

		MemCachedClient mcc = new MemCachedClient();
		resultid = mcc.set("id", id);
		resultmsg = mcc.set("msg", message);

		if (resultid && resultmsg) {
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} else {
			fullmsg = "Set Error.";
			LOG.warning(fullmsg);
			throw new Exception(fullmsg);
		}
		return fullmsg;
	}

	@Override
	public String getMsg() throws Exception {
		String fullmsg = "Error";
		MemCachedClient mcc = new MemCachedClient();

		String id = (String) mcc.get("id");
		String message = (String) mcc.get("msg");

		if (Objects.isNull(id) || Objects.isNull(message)) {
			fullmsg = "Get Error.";
			LOG.warning(fullmsg);
			throw new Exception(fullmsg);
		} else {
			fullmsg = "Received id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		}
		return fullmsg;
	}
}
