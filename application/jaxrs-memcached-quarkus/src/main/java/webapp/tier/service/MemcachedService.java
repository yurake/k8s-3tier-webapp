package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.bean.MsgBean;
import webapp.tier.exception.WebappServiceException;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class MemcachedService {

	private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String serverconf = ConfigProvider.getConfig().getValue("memcached.server.conf", String.class);

	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public MemCachedClient createMemCachedClient() {
		return new MemCachedClient();
	}

	public MsgBean setMsg(MemCachedClient mcc) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message);
		String errormsg = "Set Error.";

		try {
			boolean resultsetid = mcc.set("id", String.valueOf(msgbean.getId()));
			boolean resultsetmsg = mcc.set("msg", msgbean.getMessage());

			if (resultsetid && resultsetmsg) {
				msgbean.setFullmsg("Set");
			} else {
				LOG.warning(errormsg);
				throw new WebappServiceException(errormsg);
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new WebappServiceException(errormsg, e);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(MemCachedClient mcc) throws RuntimeException {
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
			throw new WebappServiceException(errormsg);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}
}
