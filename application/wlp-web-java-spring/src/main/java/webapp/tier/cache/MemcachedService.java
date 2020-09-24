package webapp.tier.cache;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

@Service
public class MemcachedService extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(MemcachedService.class);
	private static String serverconf = GetConfig.getResourceBundle("memcached.server.conf");
	private static String message = GetConfig.getResourceBundle("common.message");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public MemCachedClient createMemCachedClient() {
		return new MemCachedClient();
	}

	public String get() {
		String fullmsg = "Error";
		MemCachedClient mcc = createMemCachedClient();

		String id = (String) mcc.get("id");
		String message = (String) mcc.get("msg");

		if (Objects.isNull(id) || Objects.isNull(message)) {
			fullmsg = "Failed get from Memcached";
			logger.warn(fullmsg);
			throw new RuntimeException(fullmsg);
		} else {
			fullmsg = "Get id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		}
		return fullmsg;
	}

	public String set() throws NoSuchAlgorithmException {
		String fullmsg = "Error";
		String id = String.valueOf(CreateId.createid());
		boolean resultid = false;
		boolean resultmsg = false;

		MemCachedClient mcc = createMemCachedClient();
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
