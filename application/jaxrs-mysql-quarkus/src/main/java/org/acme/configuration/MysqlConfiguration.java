package org.acme.configuration;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MysqlConfiguration {

	@ConfigProperty(name = "mysql.url")
	String url;

	@ConfigProperty(name = "insert.msg")
	String sql;

	@ConfigProperty(name = "insert.msg.id")
	String sqlkey;

	@ConfigProperty(name = "insert.msg.body")
	String sqlbody;

	@ConfigProperty(name = "common.message")
	String message;

	public String getUrl() {
		return url;
	}

	public String getSql() {
		return sql;
	}

	public String getSqlkey() {
		return sqlkey;
	}

	public String getSqlbody() {
		return sqlbody;
	}

	public String getMessage() {
		return message;
	}
}
