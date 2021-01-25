package webapp.tier.healthcheck;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessMemoryCheck implements HealthCheck {
 @Override
 public HealthCheckResponse call() {
        // status is up if used memory is < 90% of max
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long memUsed = memoryBean.getHeapMemoryUsage().getUsed();
        long memMax = memoryBean.getHeapMemoryUsage().getMax();

        HealthCheckResponse response = HealthCheckResponse.named("heap-memory")
                .withData("used", memUsed)
                .withData("max", memMax)
                .state(memUsed < memMax * 0.9)
                .build();
        return response;
    }
}
