package webapp.tier.resource;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative()
@Priority(1)
@ApplicationScoped
public class MockRandomResource extends RandomResource {

	@Override
	public String random() {
		return "delivered";

	}

}
