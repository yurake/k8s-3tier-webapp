package batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import batch.util.GetConfig;

public class InsertDb {
	private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("insert.msg.body");
	private static String url = GetConfig.getResourceBundle("mysql.url");

	public void insertMsg(String id, String body) {
		Connection con = null;
		String sql = GetConfig.getResourceBundle("insert.msg");

		try {
			sql = sql.replace(sqlkey, id);
			sql = sql.replace(sqlbody, body);
			System.out.println("Execute SQL: " + sql);

			con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
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
	}
}