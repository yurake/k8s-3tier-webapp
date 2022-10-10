package webapp.tier.mq;

import webapp.tier.util.GetConfig;

class HazelcastMqServiceTest {

	private static String queuename = GetConfig.getResourceBundle("hazelcast.queue.name");

//	@Test
//	void testPutQueueHazelcast() {
//		HazelcastInstance mockInstance = null;
//		try {
//			HazelcastMqService svc = new HazelcastMqService();
//			mockInstance = Hazelcast.newHazelcastInstance();
//			svc.putQueueHazelcast(mockInstance);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		} finally {
//			if (mockInstance != null) {
//				mockInstance.shutdown();
//			}
//		}
//	}
//
//	@Test
//	void testGetQueueHazelcastNoData() {
//		HazelcastInstance mockInstance = null;
//		try {
//			HazelcastMqService svc = new HazelcastMqService();
//			mockInstance = Hazelcast.newHazelcastInstance();
//			svc.getQueueHazelcast(mockInstance);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		} finally {
//			if (mockInstance != null) {
//				mockInstance.shutdown();
//			}
//		}
//	}
//
//	@Test
//	void testGetQueueHazelcastWithData() {
//		HazelcastInstance mockInstance = null;
//		try {
//			HazelcastMqService svc = new HazelcastMqService();
//			mockInstance = Hazelcast.newHazelcastInstance();
//
//			BlockingQueue<Object> queue = mockInstance.getQueue(queuename);
//			StringBuilder buf = new StringBuilder();
//			buf.append("1111");
//			buf.append(",");
//			buf.append("Test");
//			String body = buf.toString();
//			queue.put(body);
//			svc.getQueueHazelcast(mockInstance);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		} finally {
//			if (mockInstance != null) {
//				mockInstance.shutdown();
//			}
//		}
//	}
//
//	@Test
//	void testPublishHazelcast() {
//		HazelcastInstance mockInstance = null;
//		try {
//			HazelcastMqService svc = new HazelcastMqService();
//			mockInstance = Hazelcast.newHazelcastInstance();
//			svc.publishHazelcast(mockInstance);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		} finally {
//			if (mockInstance != null) {
//				mockInstance.shutdown();
//			}
//		}
//	}
}
