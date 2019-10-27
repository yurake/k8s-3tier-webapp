package org.acme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.configuration.MysqlConfiguration;
import org.acme.util.CreateId;
import org.acme.util.json.FullMessage;
import org.eclipse.microprofile.config.Config;

@ApplicationScoped
public class MysqlService {

    @Inject
    MysqlConfiguration mysqlconfig;

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

	public Set<FullMessage> selectMysql() {

		Connection con = null;
		Set<FullMessage> returnmsg = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
		String url = config.getValue("mysql.url", String.class);
		String sql = config.getValue("select.msg.all", String.class);

		try {
			con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			System.out.println("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString("id");
				String msg = rs.getString("msg");
				returnmsg.add(new FullMessage(id, msg));

				System.out.println("Selected Msg: id: " + id + ", message: " + msg);
			}

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
	return returnmsg;
	}
}
