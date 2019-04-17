package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import servlet.util.GetConfig;

public class GetCache extends HttpServlet {
    private static String serverconf = GetConfig.getResourceBundle("cache.server.conf");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Get Cache");

	MemcachedClient mcc = new MemcachedClient();

	try {
	    String id = (String) mcc.get("id");
	    String message = (String) mcc.get("msg");
	    out.println("id: " + id);
	    out.println("msg: " + message);
	    System.out.println("Received: id: " + id + ", msg:" + message);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
