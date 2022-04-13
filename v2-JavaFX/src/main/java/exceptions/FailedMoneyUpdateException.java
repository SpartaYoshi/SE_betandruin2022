package exceptions;

public class FailedMoneyUpdateException extends Exception {

    public FailedMoneyUpdateException() {
        super();
    }

    /** Exception triggered on failed login attempts
     *@param arg0 String of the exception
     */
    public FailedMoneyUpdateException(String arg0) {
        super(arg0);
    }
}