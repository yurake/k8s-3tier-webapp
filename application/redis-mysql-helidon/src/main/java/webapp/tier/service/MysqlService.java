package webapp.tier.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import webapp.tier.util.GetConfig;

@ApplicationScoped
public class MysqlService {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String url = GetConfig.getResourceBundle("mysql.url");
	private static String instersql = GetConfig.getResourceBundle("mysql.sql");
	private static String sqlkey = GetConfig.getResourceBundle("mysql.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.body");
	private static String addonmsg = GetConfig.getResourceBundle("mysql.msg.helidon");

	public String insertMysql(String[] receivedbody) {

		String id = String.valueOf(Integer.parseInt(receivedbody[0]) + 1);

		StringBuilder builder = new StringBuilder();
		builder.append(receivedbody[1]);
		builder.append(" ");
		builder.append(addonmsg);
		String message = builder.toString();

		String sql = instersql;
		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		try (Connection con = DriverManager.getConnection(url);
				Statement stmt = con.createStatement()) {
			System.out.println("Execute SQL: " + sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Insert Error: ", e);
		}
		return sql;
	}
}
