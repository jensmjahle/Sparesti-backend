package idatt2106.systemutvikling.sparesti.exceptions;

public class ConflictException extends RuntimeException {

  public ConflictException(String message) {
    super(message);
  }

  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConflictException() {
    super("Conflict exception occurred.");
  }
}
