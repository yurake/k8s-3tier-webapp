package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

@SuppressWarnings("serial")
public class SetCache extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Set Cache");

	// コネクションプールの初期化
	SockIOPool pool = SockIOPool.getInstance();
	pool.setServers(new String[] { "memcached:11211" });
	pool.initialize();

	MemcachedClient mcc = new MemcachedClient();

	try {
	    // 値をセット
	    mcc.set("msg", "Hello oss-3tier-webapp!");
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
