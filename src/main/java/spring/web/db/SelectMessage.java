package spring.web.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import spring.web.util.GetConfig;

public class SelectMessage extends HttpServlet {
	private static String jndiname = GetConfig.getResourceBundle("jndi.name");
	private static String sql = GetConfig.getResourceBundle("select.msg.all");
	private DataSource ds;

	public List<String> selectMsg() {
		Connection con = null;
		List<String> allmsg = new ArrayList<>();

		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndiname);
			con = ds.getConnection();
			Statement stmt = con.createStatement();

			System.out.println("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
				System.out.println(fullmsg);
				allmsg.add(fullmsg);
			}

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
		return allmsg;
	}
}