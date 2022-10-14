package webapp.tier.service;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqSubscribeServiceTest2 {

	/**
	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String splitkey = ConfigProvider.getConfig()
			.getValue("rabbitmq.split.key", String.class);
	
	@Channel("message")
	Emitter<String> quoteRequestEmitter;
	
	@Test
	void testSubscribe() throws Exception {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);
		quoteRequestEmitter.send(body); 
		Thread.sleep(5000);
	}
	**/

}
