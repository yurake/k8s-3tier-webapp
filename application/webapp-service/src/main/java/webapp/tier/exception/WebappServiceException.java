package webapp.tier.exception;

public class WebappServiceException extends RuntimeException {

	public WebappServiceException(String errorMessage) {
		super(errorMessage);
	}

	public WebappServiceException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
