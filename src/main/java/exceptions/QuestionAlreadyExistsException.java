package exceptions;

public class QuestionAlreadyExistsException extends Exception {

	public QuestionAlreadyExistsException() {
		super();
	}

	/**This exception is triggered if the question already exists 
	 *@param s String of the exception
	 */
	public QuestionAlreadyExistsException(String s) {
		super(s);
	}
}