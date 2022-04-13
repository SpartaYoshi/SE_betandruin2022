package exceptions;

public class QuestionAlreadyExist extends Exception {

	public QuestionAlreadyExist() {
		super();
	}

	/**This exception is triggered if the question already exists 
	 *@param s String of the exception
	 */
	public QuestionAlreadyExist(String s) {
		super(s);
	}
}