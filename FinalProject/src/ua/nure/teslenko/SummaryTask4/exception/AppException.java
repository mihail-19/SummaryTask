package ua.nure.teslenko.SummaryTask4.exception;

/**
 * Application exception. It should be handled in Controller.
 * @author Mykhailo Teslenko
 *
 */
public class AppException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}


}
