package idatt2106.systemutvikling.sparesti.exceptions;

public class BankConnectionErrorException extends RuntimeException {

  /**
   * Constructor for BankConnectionErrorException.
   *
   * @param message the message to be displayed
   */
  public BankConnectionErrorException(String message) {
    super(message);
  }

  /**
   * Constructor for BankConnectionErrorException.
   */
  public BankConnectionErrorException() {
    super("Could not connect to bank.");
  }

  /**
   * Constructor for BankConnectionErrorException.
   *
   * @param message the message to be displayed
   * @param cause the cause of the exception
   */
  public BankConnectionErrorException(String message, Throwable cause) {
    super(message, cause);
  }

}
