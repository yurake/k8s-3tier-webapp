package webapp.tier.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
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
	public String setMsg() throws RuntimeException, NoSuchAlgorithmException {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);

		try {
			client = ConnectHazelcast.getInstance();
			Map<Integer, String> map = client.getMap(cachename);
			map.put(msgbean.getId(), msgbean.getMessage());
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Set Error.", e);
			throw new RuntimeException("Set Error.");
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
	public String getMsg() throws RuntimeException {
		List<String> msglist = getMsgList();
		return msglist.get(msglist.size() - 1);

	}

	public List<String> getMsgList() throws RuntimeException {
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
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
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
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}
}
