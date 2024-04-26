package idatt2106.systemutvikling.sparesti.exceptions;

public class BankConnectionErrorException extends RuntimeException {

  public BankConnectionErrorException(String message) {
    super(message);
  }

  public BankConnectionErrorException() {
    super("Could not connect to bank.");
  }

  public BankConnectionErrorException(String message, Throwable cause) {
    super(message, cause);
  }

}
