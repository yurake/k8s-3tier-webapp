package spring.web.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import spring.web.util.GetConfig;

public class ConnectMysql {

	private static String jndiname = GetConfig.getResourceBundle("jndi.name");
	private DataSource ds;
	private Connection con = null;

	public Connection getConnection() throws SQLException, NamingException {
		InitialContext ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(jndiname);
		return ds.getConnection();
	}

	public boolean connectionStatus() {
		boolean status = false;
		try {
			con = getConnection();
			status = true;
		} catch (SQLException | NamingException e) {
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
		return status;
	}
}
