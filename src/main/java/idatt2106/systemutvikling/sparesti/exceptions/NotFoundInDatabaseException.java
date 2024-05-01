package idatt2106.systemutvikling.sparesti.exceptions;

public class NotFoundInDatabaseException extends RuntimeException {

  /**
   * Constructor for NotFoundInDatabaseException.
   *
   * @param message the message to be displayed
   */
  public NotFoundInDatabaseException(String message) {
    super(message);
  }

  /**
   * Constructor for NotFoundInDatabaseException.
   *
   * @param message the message to be displayed
   * @param cause the cause of the exception
   */
  public NotFoundInDatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for NotFoundInDatabaseException.
   */
  public NotFoundInDatabaseException() {
    super("Not found in database.");
  }
}
