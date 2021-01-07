package webapp.tier.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import webapp.tier.resource.ActiveMqResource;
import webapp.tier.resource.HazelcastResource;
import webapp.tier.resource.MemcachedResource;
import webapp.tier.resource.MysqlResource;
import webapp.tier.resource.PostgresResource;
import webapp.tier.resource.RabbitmqResource;
import webapp.tier.resource.RedisResource;

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
		classes.add(PostgresResource.class);
		return classes;
	}
}
