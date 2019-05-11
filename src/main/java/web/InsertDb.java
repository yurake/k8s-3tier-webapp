package web;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.util.CreateId;
import web.util.GetConfig;

@Controller
public class InsertDb {
	private static String jndiname = GetConfig.getResourceBundle("jndi.name");
	private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private DataSource ds;

	@RequestMapping(value = "InsertDb", method = RequestMethod.GET)
	public String insertDb() {
		return doGet();
	}

	public String doGet() {
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
		return "Insert DB";
	}
}