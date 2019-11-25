package org.acme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.util.CreateId;
import org.eclipse.microprofile.config.Config;

@ApplicationScoped
public class MysqlService {

    @Inject
    Config config;

	public String insertMysql() {

		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String url = config.getValue("mysql.url", String.class);
		String instersql = config.getValue("insert.msg", String.class);
		String sqlkey = config.getValue("insert.msg.id", String.class);
		String sqlbody = config.getValue("insert.msg.body", String.class);
		String message = config.getValue("common.message", String.class);

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

	public String insertMysql(String[] receivedbody) {

		Connection con = null;
		String id = receivedbody[0];
		String url = config.getValue("mysql.url", String.class);
		String instersql = config.getValue("insert.msg", String.class);
		String sqlkey = config.getValue("insert.msg.id", String.class);
		String sqlbody = config.getValue("insert.msg.body", String.class);
		String message = receivedbody[1];

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
