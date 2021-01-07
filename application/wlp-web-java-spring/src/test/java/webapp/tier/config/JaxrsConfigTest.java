package webapp.tier.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

import webapp.tier.resource.ActiveMqResource;
import webapp.tier.resource.HazelcastResource;
import webapp.tier.resource.MemcachedResource;
import webapp.tier.resource.MysqlResource;
import webapp.tier.resource.PostgresResource;
import webapp.tier.resource.RabbitmqResource;
import webapp.tier.resource.RedisResource;

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
