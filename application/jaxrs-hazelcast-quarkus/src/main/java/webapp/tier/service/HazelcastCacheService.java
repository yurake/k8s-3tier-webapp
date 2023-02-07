package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class HazelcastCacheService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "hazelcast.cache.name")
	String cachename;

	public MsgBean setMsg(HazelcastInstance client) throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Set");
		try {
			Map<Integer, String> map = client.getMap(cachename);
			map.put(msgbean.getId(), msgbean.getMessage());
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		logger.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(HazelcastInstance client) {
		List<MsgBean> msglist = getMsgList(client);
		return msglist.get(msglist.size() - 1);

	}

	public List<MsgBean> getMsgList(HazelcastInstance client) {
		List<MsgBean> msglist = new ArrayList<>();

		try {
			Map<Integer, String> map = client.getMap(cachename);

			for (Entry<Integer, String> entry : map.entrySet()) {
				MsgBean msgbean = new MsgBean(entry.getKey(), entry.getValue(), "Get");
				logger.info(msgbean.getFullmsg());
				msglist.add(msgbean);
			}
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Get"));
			}
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return msglist;
	}
}
