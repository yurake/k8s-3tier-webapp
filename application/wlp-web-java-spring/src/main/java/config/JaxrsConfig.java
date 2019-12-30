package config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import resource.MemcachedResource;
import resource.MysqlResource;
import resource.RabbitmqResource;
import resource.RedisResource;

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
