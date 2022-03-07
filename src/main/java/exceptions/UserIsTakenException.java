package exceptions;

public class UserIsTakenException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserIsTakenException() {
		super();
	}


	public UserIsTakenException(String arg0) {
		super(arg0);
	}
}
