package idatt2106.systemutvikling.sparesti.exceptions;

public class InvalidTokenException extends RuntimeException {

  /**
   * Constructor for InvalidTokenException.
   *
   * @param message the message to be displayed
   */
  public InvalidTokenException(String message) {
    super(message);
  }

  /**
   * Constructor for InvalidTokenException.
   *
   * @param message the message to be displayed
   * @param cause the cause of the exception
   */
  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for InvalidTokenException.
   */
  public InvalidTokenException() {
    super("Invalid token.");
  }
}
