package batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import batch.util.CreateId;
import batch.util.GetConfig;

public class InsertDb {
	private static String jndiname = GetConfig.getResourceBundle("jndi.name");
	private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
	private DataSource ds;

	public void doGet() {
		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String sql = GetConfig.getResourceBundle("insert.msg");

		try {
			sql = sql.replace(sqlkey, id);
			System.out.println("Execute SQL: " + sql);

			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndiname);
			con = ds.getConnection();
			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);

			System.out.println("Selected Msg: id: " + id + ", sql: " + sql);
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
	}
}