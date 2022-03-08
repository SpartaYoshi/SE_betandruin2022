package exceptions;

public class FeeAlreadyExists extends Exception {
	private static final long serialVersionUID = 1L;

	public FeeAlreadyExists() {
		super();
	}


	public FeeAlreadyExists(String s) {
		super(s);
	}
}