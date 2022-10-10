package webapp.tier.cache;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import webapp.tier.bean.MsgBean;
import webapp.tier.exception.WebappServiceException;

@Service
public class MemcachedService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${common.message}")
	private String message;

	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { "memcached:11211" });
		pool.initialize();
	}

	public MemCachedClient createMemCachedClient() {
		return new MemCachedClient();
	}

	protected MsgBean checkResult(int id, String msg, String errormsg) {
		if (Objects.isNull(id) || Objects.isNull(msg)) {
			logger.warn(errormsg);
			throw new WebappServiceException(errormsg);
		}
		return new MsgBean(id, msg);
	}

	public MsgBean get() {
		MsgBean msgbean = null;
		String errormsg = "Failed get from Memcached";
		MemCachedClient mcc = createMemCachedClient();

		try {
			int id = (int) mcc.get("id");
			String msg = (String) mcc.get("msg");
			msgbean = checkResult(id, msg, errormsg);
		} catch (Exception e) {
			logger.warn(errormsg, e);
			throw new WebappServiceException(errormsg);
		}

		logger.info(msgbean.logMessageOut("get"));
		return msgbean;
	}

	public MsgBean set() {
		MsgBean msgbean = new MsgBean(message);
		boolean resultid = false;
		boolean resultmsg = false;

		MemCachedClient mcc = createMemCachedClient();
		resultid = mcc.set("id", msgbean.getId());
		resultmsg = mcc.set("msg", message);

		if (resultid && resultmsg) {
			logger.info(msgbean.logMessageOut("set"));
		} else {
			String errormsg = "Failed set to Memcached";
			logger.warn(errormsg);
			throw new WebappServiceException(errormsg);
		}
		return msgbean;
	}
}
