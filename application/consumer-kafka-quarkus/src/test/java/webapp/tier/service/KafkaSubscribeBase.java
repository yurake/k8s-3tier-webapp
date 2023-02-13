package webapp.tier.service;

import javax.inject.Inject;

abstract class KafkaSubscribeBase {
	@Inject
	KafkaSubscribeService svc;

	abstract void testProcess();

	abstract void testProcessKafkaCompanion();
	
	abstract KafkaSubscribeService getSvc();
}
