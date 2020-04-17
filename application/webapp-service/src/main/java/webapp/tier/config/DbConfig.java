package webapp.tier.config;

public class DbConfig {

	String message;
	String url;
	String insertsql;
	String selectsql;
	String deletesql;
	String sqlkey;
	String sqlbody;

	public DbConfig(String message, String url, String insertsql, String selectsql, String deletesql, String sqlkey,
			String sqlbody) {
		this.message = message;
		this.url = url;
		this.insertsql = insertsql;
		this.selectsql = selectsql;
		this.deletesql = deletesql;
		this.sqlkey = sqlkey;
		this.sqlbody = sqlbody;
	}

	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}

	public String getInsertsql() {
		return insertsql;
	}

	public String getSelectsql() {
		return selectsql;
	}

	public String getDeletesql() {
		return deletesql;
	}

	public String getSqlkey() {
		return sqlkey;
	}

	public String getSqlbody() {
		return sqlbody;
	}
}
