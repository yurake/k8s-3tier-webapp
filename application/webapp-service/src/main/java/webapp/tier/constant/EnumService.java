package webapp.tier.constant;

public enum EnumService {
	common_message("Hello k8s-3tier-webapp with quarkus!"),
	mysql_url("jdbc:mysql://mysql:3306/webapp?user=webapp&password=webapp&autoReconnect=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC"),
	mysql_insert_msg("INSERT INTO msg (id, msg) VALUES (msgid, 'msgbody')"),
	mysql_select_msg_all("SELECT * FROM msg"),
	mysql_delete_msg_all("DELETE FROM msg"),
	mysql_id("msgid"),
	mysql_body("msgbody"),
	mysql_msg_quarkus("throuth quarkus"),
	redis_server("redis"),
	redis_port("6379"),
	redis_set_expire("3600"),
	redis_channel("pubsub"),
	redis_splitkey(","),
	postgres_url("jdbc:mysql://postgres:5432/webapp?user=webapp&password=webapp"),
	postgres_select_msg_all("SELECT * FROM msg"),
	postgres_delete_msg_all("DELETE FROM msg"),
	postgres_insert_msg("INSERT INTO msg (id, msg) VALUES (msgid, 'msgbody')"),
	postgres_insert_msg_id("msgid"),
	postgres_insert_msg_body("msgbody"),
	activemq_url("tcp://activemq:61616"),
	activemq_splitkey(","),
	activemq_queue_name("ActiveMQueue"),
	activemq_opic_name("ActiveMQTopic"),
	hazelcast_group_name("dev"),
	hazelcast_service_name("hazelcast.default.svc.cluster.local"),
	hazelcast_cache_name("hazcache"),
	hazelcast_queue_name("hazqueue"),
	hazelcast_topicname_name("haztopic"),
	hazelcast_split_key(","),
	memcached_server_conf("memcached:11211"),
	;

    private String config;

	private EnumService(final String config) {
        this.config = config;
    }

    public String getString() {
        return this.config;
    }
}
