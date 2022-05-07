package exceptions;

public class FailedFetchException extends Exception {

    public FailedFetchException() {
        super();
    }

    /** Exception triggered on failed login attempts
     *@param arg0 String of the exception
     */
    public FailedFetchException(String arg0) {
        super(arg0);
    }
}