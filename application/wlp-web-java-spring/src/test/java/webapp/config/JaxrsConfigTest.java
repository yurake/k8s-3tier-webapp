package webapp.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import webapp.resource.ActiveMqResource;
import webapp.resource.HazelcastResource;
import webapp.resource.MemcachedResource;
import webapp.resource.MysqlResource;
import webapp.resource.PostgresResource;
import webapp.resource.RabbitmqResource;
import webapp.resource.RedisResource;

class JaxrsConfigTest {

	@Test
	void testgetClasses() {
		JaxrsConfig cnf = new JaxrsConfig();
		Set<Class<?>> actual = cnf.getClasses();
		assertThat(actual.contains(MemcachedResource.class), is(true));
		assertThat(actual.contains(MysqlResource.class), is(true));
		assertThat(actual.contains(RabbitmqResource.class), is(true));
		assertThat(actual.contains(RedisResource.class), is(true));
		assertThat(actual.contains(HazelcastResource.class), is(true));
		assertThat(actual.contains(ActiveMqResource.class), is(true));
		assertThat(actual.contains(PostgresResource.class), is(true));
	}

}
