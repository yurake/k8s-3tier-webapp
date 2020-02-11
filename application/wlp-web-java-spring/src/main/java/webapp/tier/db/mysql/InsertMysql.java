package webapp.tier.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class InsertMysql {

	Logger logger = LoggerFactory.getLogger(InsertMysql.class);
	private static String sqlkey = GetConfig.getResourceBundle("mysql.insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");

	public String insertMysql() throws SQLException, NamingException {
		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String sql = GetConfig.getResourceBundle("mysql.insert.msg");

		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		ConnectMysql conmysql = new ConnectMysql();
		con = conmysql.getConnection();
		Statement stmt = con.createStatement();

		logger.info("Execute SQL: " + sql);
		stmt.executeUpdate(sql);

		if (con != null) {
			con.close();
		}
		return sql;
	}
}
