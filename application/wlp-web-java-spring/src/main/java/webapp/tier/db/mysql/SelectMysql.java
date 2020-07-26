package webapp.tier.db.mysql;

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

import webapp.tier.util.GetConfig;

public class SelectMysql extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(SelectMysql.class);
	private static String sql = GetConfig.getResourceBundle("mysql.select.msg.all");

	public List<String> selectMsg() throws SQLException, NamingException {
		List<String> allmsg = new ArrayList<>();
		ConnectMysql conmysql = new ConnectMysql();
		Connection con = conmysql.getConnection();
		Statement stmt = con.createStatement();

		logger.info("Execute SQL: " + sql);
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
			logger.info(fullmsg);
			allmsg.add(fullmsg);
		}
		if (allmsg.isEmpty()) {
			allmsg.add("No Data");
		}

		if (con != null) {
			con.close();
		}
		return allmsg;
	}
}