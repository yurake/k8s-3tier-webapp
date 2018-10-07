package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@SuppressWarnings("serial")
public class SelectDb extends HttpServlet {
    private final static String JNDI_NAME = "java:comp/env/jdbc/OssaplDS";
    private DataSource ds;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Select DB");

	Connection con = null;
	String id = null;
	String msg = null;
	try {
	    InitialContext ctx = new InitialContext();
	    ds = (DataSource) ctx.lookup(JNDI_NAME);
	    con = ds.getConnection();
	    Statement stmt = con.createStatement();

	    String sql = "select * from msg"; // TODO SQLベタガキ
	    ResultSet rs = stmt.executeQuery(sql);
	    while (rs.next()) {
		id = rs.getString("id");
		msg = rs.getString("msg");
		out.println("id: " + id + ", message: " + msg);
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
    }
}