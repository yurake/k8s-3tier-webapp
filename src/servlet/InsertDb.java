package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import servlet.util.CreateId;

@SuppressWarnings("serial")
public class InsertDb extends HttpServlet {
    private final static String JNDI_NAME = "java:comp/env/jdbc/OssaplDS";
    private DataSource ds;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	Connection con = null;
	int id = CreateId.createid();

	PrintWriter out = response.getWriter();
	out.println("Insert DB");

	try {
	    InitialContext ctx = new InitialContext();
	    ds = (DataSource) ctx.lookup(JNDI_NAME);
	    con = ds.getConnection();
	    Statement stmt = con.createStatement();

	    StringBuffer buf = new StringBuffer();
	    buf.append("INSERT INTO msg (id, msg) VALUES (");
	    buf.append(String.valueOf(id));
	    buf.append(", 'Hello oss-3tier-webapp!');");
	    String sql = buf.toString();

	    System.out.println("Execute SQL: " + sql);
	    out.println("Execute SQL: " + sql);
	    stmt.executeUpdate(sql);
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