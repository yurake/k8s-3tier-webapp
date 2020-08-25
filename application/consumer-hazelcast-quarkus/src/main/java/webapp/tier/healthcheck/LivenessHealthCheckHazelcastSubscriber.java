package webapp.tier.healthcheck;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthCheckHazelcastSubscriber extends LivenessHealthCheckHazelcast {
}
