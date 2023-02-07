package webapp.tier.service;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.bean.MsgBean;
import webapp.tier.exception.WebappServiceException;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class MemcachedService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String serverconf = ConfigProvider.getConfig().getValue(
			"memcached.server.conf",
			String.class);

	@ConfigProperty(name = "common.message")
	String message;

	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

	public MemCachedClient createMemCachedClient() {
		return new MemCachedClient();
	}

	public MsgBean setMsg(MemCachedClient mcc) {

		MsgBean msgbean = null;
		String errormsg = "Set Error.";
		try {
			msgbean = new MsgBean(CreateId.createid(), message);
			Objects.requireNonNull(msgbean);
			boolean resultsetid = mcc.set("id", String.valueOf(msgbean.getId()));
			boolean resultsetmsg = mcc.set("msg", msgbean.getMessage());

			if (resultsetid && resultsetmsg) {
				msgbean.setFullmsg("Set");
			} else {
				logger.log(Level.WARNING, errormsg);
				throw new WebappServiceException(errormsg);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, errormsg, e);
			throw new WebappServiceException(errormsg, e);
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(MemCachedClient mcc) throws RuntimeException {
		MsgBean msgbean = new MsgBean(0, "No Data.", "Get");
		String errormsg = "Get Error.";

		try {
			String getid = (String) mcc.get("id");
			if (!Objects.isNull(getid) && !getid.isEmpty()) {
				msgbean = new MsgBean(MsgUtils.stringToInt(getid),
						(String) mcc.get("msg"), "Get");
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, errormsg, e);
			throw new WebappServiceException(errormsg);
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}
}
