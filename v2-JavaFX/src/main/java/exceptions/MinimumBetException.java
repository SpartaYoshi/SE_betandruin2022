package exceptions;

public class MinimumBetException extends Exception {
    private static final long serialVersionUID = 1L;

    public MinimumBetException() {
        super();
    }

    /**This exception is triggered if the user wants to bet an amount of money larger than the available he/she has
     *@param arg String of the exception
     */
    public MinimumBetException(String arg) {
        super(arg);
    }
}