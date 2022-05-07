package exceptions;

public class EventAlreadyFinishedException extends Exception {

	public EventAlreadyFinishedException() {
		super();
	}
	
	/**This exception is triggered if the event has already finished
	 *@param s String of the exception
	 */
	public EventAlreadyFinishedException(String s) {
		super(s);
	}
}