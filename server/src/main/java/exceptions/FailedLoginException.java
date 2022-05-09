package exceptions;

public class FailedLoginException extends Exception {

	public FailedLoginException() {
		super();
	}
	
	/** Exception triggered on failed login attempts
	 *@param arg0 String of the exception
	 */
	public FailedLoginException(String arg0) {
		super(arg0);
	}
}