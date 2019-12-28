package spring.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import spring.resource.MemcachedResource;
import spring.resource.MysqlResource;
import spring.resource.RabbitmqResource;
import spring.resource.RedisResource;

@ApplicationPath("/api/*")
public class JaxrsConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(MemcachedResource.class);
		classes.add(MysqlResource.class);
		classes.add(RabbitmqResource.class);
		classes.add(RedisResource.class);
		return classes;
	}
}
