package idatt2106.systemutvikling.sparesti.exceptions;

/**
 * Exception for when a conflict occurs.
 */
public class ConflictException extends RuntimeException {

  /**
   * Constructor for ConflictException.
   *
   * @param message the message to be displayed
   */
  public ConflictException(String message) {
    super(message);
  }

  /**
   * Constructor for ConflictException.
   *
   * @param message the message to be displayed
   * @param cause   the cause of the exception
   */
  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for ConflictException.
   */
  public ConflictException() {
    super("Conflict exception occurred.");
  }
}
