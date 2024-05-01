package idatt2106.systemutvikling.sparesti.exceptions;

public class OpenAIException extends RuntimeException {

  /**
   * Constructor for OpenAIException.
   *
   * @param message the message to be displayed
   */
  public OpenAIException(String message) {
    super(message);
  }

  /**
   * Constructor for OpenAIException.
   *
   * @param message the message to be displayed
   * @param cause the cause of the exception
   */
  public OpenAIException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for OpenAIException.
   */
  public OpenAIException() {
    super("OpenAI exception occurred.");
  }
}
