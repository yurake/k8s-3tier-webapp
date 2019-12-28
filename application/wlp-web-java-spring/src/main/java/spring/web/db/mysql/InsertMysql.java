package spring.web.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class InsertMysql {

	private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");

	public String insertMysql() throws SQLException, NamingException {
		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String sql = GetConfig.getResourceBundle("insert.msg");

		try {
			sql = sql.replace(sqlkey, id);
			sql = sql.replace(sqlbody, message);

			ConnectMysql conmysql = new ConnectMysql();
			con = conmysql.getConnection();
			Statement stmt = con.createStatement();

			System.out.println("Execute SQL: " + sql);
			stmt.executeUpdate(sql);

		}  finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return sql;
	}
}
