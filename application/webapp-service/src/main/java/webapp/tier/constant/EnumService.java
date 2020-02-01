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
	redis_channel("pubsub"),
	redis_splitkey(","),
	;

    private String config;

	private EnumService(final String config) {
        this.config = config;
    }

    public String getString() {
        return this.config;
    }
}
