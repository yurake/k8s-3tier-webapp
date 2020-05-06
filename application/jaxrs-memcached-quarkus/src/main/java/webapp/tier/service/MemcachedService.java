package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Cache;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

public class MemcachedService implements Cache {

	private static final Logger LOG = Logger.getLogger(MemcachedService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String serverconf = ConfigProvider.getConfig().getValue("memcached.server.conf", String.class);

	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	@Override
	public MsgBean setMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message);
		String errormsg = "Set Error.";

		try {
			MemCachedClient mcc = new MemCachedClient();
			boolean resultsetid = mcc.set("id", String.valueOf(msgbean.getId()));
			boolean resultsetmsg = mcc.set("msg", msgbean.getMessage());

			if (resultsetid && resultsetmsg) {
				msgbean.setFullmsg("Set");
			} else {
				LOG.warning(errormsg);
				throw new RuntimeException(errormsg);
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean getMsg() throws RuntimeException {
		MemCachedClient mcc = new MemCachedClient();
		MsgBean msgbean = null;
		String getid = null;
		String errormsg = "Get Error.";

		try {
			getid = (String) mcc.get("id");
			if (Objects.isNull(getid) || getid.toString().isEmpty()) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = new MsgBean(MsgUtils.stringToInt(getid), (String) mcc.get("msg"), "Get");
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}
}
