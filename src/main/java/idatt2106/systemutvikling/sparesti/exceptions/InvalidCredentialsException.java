package idatt2106.systemutvikling.sparesti.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  /**
   * Constructor for InvalidCredentialsException.
   *
   * @param message the message to be displayed
   */
  public InvalidCredentialsException(String message) {
    super(message);
  }

  /**
   * Constructor for InvalidCredentialsException.
   *
   * @param message the message to be displayed
   * @param cause the cause of the exception
   */
  public InvalidCredentialsException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for InvalidCredentialsException.
   */
  public InvalidCredentialsException() {
    super("Invalid credentials.");
  }

}
