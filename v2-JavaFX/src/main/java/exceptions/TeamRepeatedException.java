package exceptions;

public class TeamRepeatedException extends Exception{

	private static final long serialVersionUID = 1L;

	public TeamRepeatedException() {
		super();
	}


	public TeamRepeatedException(String s) {
		super(s);
	}
	
}
