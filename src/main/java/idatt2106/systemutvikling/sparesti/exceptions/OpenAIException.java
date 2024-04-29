package idatt2106.systemutvikling.sparesti.exceptions;

public class OpenAIException extends RuntimeException {

  public OpenAIException(String message) {
    super(message);
  }

  public OpenAIException(String message, Throwable cause) {
    super(message, cause);
  }

  public OpenAIException() {
    super("OpenAI exception occurred.");
  }
}
