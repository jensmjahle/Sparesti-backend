package idatt2106.systemutvikling.sparesti.exceptions;


/**
 * Exception for when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructor for UserNotFoundException.
   *
   * @param message the message to be displayed
   */
  public UserNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructor for UserNotFoundException.
   *
   * @param message the message to be displayed
   * @param cause   the cause of the exception
   */
  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for UserNotFoundException.
   */
  public UserNotFoundException() {
    super("User not found.");
  }

}
