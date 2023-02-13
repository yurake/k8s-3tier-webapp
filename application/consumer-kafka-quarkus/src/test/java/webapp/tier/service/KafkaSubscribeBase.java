package webapp.tier.service;

abstract class KafkaSubscribeBase {

	abstract void testProcess();

	abstract void testProcessKafkaCompanion();
	
	abstract KafkaSubscribeService getSvc();
}
