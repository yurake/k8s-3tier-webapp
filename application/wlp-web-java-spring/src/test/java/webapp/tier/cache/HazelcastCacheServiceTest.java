package webapp.tier.cache;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import webapp.tier.cache.HazelcastCacheService;
import webapp.tier.util.GetConfig;

class HazelcastCacheServiceTest {

	private static String cachename = GetConfig.getResourceBundle("hazelcast.cache.name");

	@Test
	void testPutMapHazelcast() throws Exception {
		HazelcastInstance mockInstance = null;
		try {
			HazelcastCacheService svc = new HazelcastCacheService();
			mockInstance = Hazelcast.newHazelcastInstance();
			svc.putMapHazelcast(mockInstance);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			if (mockInstance != null) {
				mockInstance.shutdown();
			}
		}
	}

	@Test
	void testGetMapHazelcastNoData() throws Exception {
		HazelcastInstance mockInstance = null;
		try {
			HazelcastCacheService svc = new HazelcastCacheService();
			mockInstance = Hazelcast.newHazelcastInstance();
			svc.getMapHazelcast(mockInstance);
		} catch (Exception e) {
			e.printStackTrace();
			fail("まだ実装されていません");
		} finally {
			if (mockInstance != null) {
				mockInstance.shutdown();
			}
		}
	}

	@Test
	void testGetMapHazelcastWithData() throws Exception {
		HazelcastInstance mockInstance = null;
		try {
			HazelcastCacheService svc = new HazelcastCacheService();
			mockInstance = Hazelcast.newHazelcastInstance();
			Map<String, String> map = mockInstance.getMap(cachename);
			map.put("1111", "Test");
			svc.getMapHazelcast(mockInstance);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			if (mockInstance != null) {
				mockInstance.shutdown();
			}
		}
	}
}
