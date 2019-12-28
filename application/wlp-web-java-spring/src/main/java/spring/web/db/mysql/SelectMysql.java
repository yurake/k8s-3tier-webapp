package spring.web.db.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spring.web.util.GetConfig;

public class SelectMysql extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(SelectMysql.class);
	private static String sql = GetConfig.getResourceBundle("select.msg.all");

	public List<String> selectMsg() throws SQLException, NamingException {
		Connection con = null;
		List<String> allmsg = new ArrayList<>();

		try {
			ConnectMysql conmysql = new ConnectMysql();
			con = conmysql.getConnection();
			Statement stmt = con.createStatement();

			logger.info("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return allmsg;
	}
}