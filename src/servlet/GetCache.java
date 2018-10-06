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
public class GetCache extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Get Cache");

	// コネクションプールの初期化
	SockIOPool pool = SockIOPool.getInstance();
	pool.setServers(new String[] { "memcached:11211" });
	pool.initialize();

	MemcachedClient mcc = new MemcachedClient();

	try {
	    // 値を取得して表示
	    String message = (String) mcc.get("msg");
	    out.println("Received Msg: '" + message + "'");
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
