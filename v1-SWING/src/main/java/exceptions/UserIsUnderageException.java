package exceptions;

public class UserIsUnderageException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserIsUnderageException() {
		super();
	}


	public UserIsUnderageException(String arg0) {
		super(arg0);
	}
}
