package webapp.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import webapp.resource.ActiveMqResource;
import webapp.resource.HazelcastResource;
import webapp.resource.MemcachedResource;
import webapp.resource.MysqlResource;
import webapp.resource.RabbitmqResource;
import webapp.resource.RedisResource;

@ApplicationPath("/api/*")
public class JaxrsConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(MemcachedResource.class);
		classes.add(MysqlResource.class);
		classes.add(RabbitmqResource.class);
		classes.add(RedisResource.class);
		classes.add(HazelcastResource.class);
		classes.add(ActiveMqResource.class);
		return classes;
	}
}
