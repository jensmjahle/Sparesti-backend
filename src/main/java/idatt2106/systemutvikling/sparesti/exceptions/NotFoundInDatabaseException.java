package idatt2106.systemutvikling.sparesti.exceptions;

public class NotFoundInDatabaseException extends RuntimeException {

  public NotFoundInDatabaseException(String message) {
    super(message);
  }

  public NotFoundInDatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundInDatabaseException() {
    super("Not found in database.");
  }
}
