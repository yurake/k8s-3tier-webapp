package org.acme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MysqlService {

	public String insertMysql(String[] receivedbody) {

		Connection con = null;
		String id = receivedbody[0];
		String url = "jdbc:mysql://mysql:3306/webapp?user=mysql&password=mysql&autoReconnect=true&useSSL=false&serverTimezone=UTC";
		String instersql = "INSERT INTO msg (id, msg) VALUES (msgid, 'msgbody')";
		String sqlkey = "msgid";
		String sqlbody = "msgbody";

		StringBuilder builder = new StringBuilder();
		builder.append(receivedbody[1]);
		builder.append(" throuth quarkus");
		String message = builder.toString();

		String sql = instersql;
		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		try {
			con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			System.out.println("Execute SQL: " + sql);
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
		return sql;
	}
}
