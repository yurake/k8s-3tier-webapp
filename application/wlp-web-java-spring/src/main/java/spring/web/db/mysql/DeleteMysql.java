package spring.web.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.web.util.GetConfig;

public class DeleteMysql extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(DeleteMysql.class);
	private static String sql = GetConfig.getResourceBundle("delete.msg.all");

	public void deleteMsg() throws SQLException, NamingException {
		Connection con = null;

		try {
			ConnectMysql conmysql = new ConnectMysql();
			con = conmysql.getConnection();
			Statement stmt = con.createStatement();

			logger.info("Execute SQL: " + sql);
			stmt.executeUpdate(sql);

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