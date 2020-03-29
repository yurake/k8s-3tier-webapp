package webapp.tier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.interfaces.Cache;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

public class HazelcastCacheService implements Cache {

	private static Logger LOG = Logger.getLogger(HazelcastCacheService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String cachename = ConfigProvider.getConfig().getValue("hazelcast.cache.name", String.class);

	@Override
	public String setMsg() throws Exception {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);

		try {
			client = ConnectHazelcast.getInstance();
			Map<Integer, String> map = client.getMap(cachename);
			map.put(msgbean.getId(), msgbean.getMessage());


		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Set Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		msgbean.setFullmsgWithType(msgbean, "Set");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws Exception {
		List<String> msglist = getMsgList();
		return msglist.get(msglist.size() - 1);

	}

	public List<String> getMsgList() throws Exception {
		List<String> msglist = new ArrayList<>();
		HazelcastInstance client = null;

		try {
			client = ConnectHazelcast.getInstance();
			Map<Integer, String> map = client.getMap(cachename);

			for (Entry<Integer, String> entry : map.entrySet()) {
				MsgBeanUtils msgbean = new MsgBeanUtils();
				msgbean.setId(entry.getKey());
				msgbean.setMessage(entry.getValue());
				msgbean.setFullmsgWithType(msgbean, "Get");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}

			if (msglist.isEmpty()) {
				msglist.add("No Data");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return msglist;
	}

	public boolean isActive() {
		HazelcastInstance client = null;
		boolean status = false;
		try {
			client = ConnectHazelcast.getInstance();
			status = client.getLifecycleService().isRunning();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}
}
