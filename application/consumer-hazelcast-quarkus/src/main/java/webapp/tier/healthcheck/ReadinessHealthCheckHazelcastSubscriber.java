package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheckHazelcastSubscriber extends ReadinessHealthCheckHazelcast {
}
