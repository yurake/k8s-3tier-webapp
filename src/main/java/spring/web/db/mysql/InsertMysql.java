package spring.web.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class InsertMysql {

	private static String jndiname = GetConfig.getResourceBundle("jndi.name");
	private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private DataSource ds;

	public String insertMysql() {
		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String sql = GetConfig.getResourceBundle("insert.msg");

		try {
			sql = sql.replace(sqlkey, id);
			sql = sql.replace(sqlbody, message);

			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndiname);
			con = ds.getConnection();
			Statement stmt = con.createStatement();

			System.out.println("Execute SQL: " + sql);
			stmt.executeUpdate(sql);

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
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
