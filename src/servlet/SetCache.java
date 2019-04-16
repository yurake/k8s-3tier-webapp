package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import servlet.util.CreateId;
import servlet.util.GetConfig;

public class SetCache extends HttpServlet {
    private static String serverconf = GetConfig.getResourceBundle("cache.server.conf");
    private static String message = GetConfig.getResourceBundle("common.message");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Set Cache");

	// コネクションプールの初期化
	SockIOPool pool = SockIOPool.getInstance();
	pool.setServers(new String[] { serverconf });
	pool.initialize();

	String id = String.valueOf(CreateId.createid());
	out.println("id: " + id);
	out.println("msg: " + message);

	MemcachedClient mcc = new MemcachedClient();
	try {
	    mcc.set("id", id);
	    mcc.set("msg", message);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
