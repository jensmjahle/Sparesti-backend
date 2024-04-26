package idatt2106.systemutvikling.sparesti.handler;

import idatt2106.systemutvikling.sparesti.exceptions.BankConnectionErrorException;
import idatt2106.systemutvikling.sparesti.exceptions.ConflictException;
import idatt2106.systemutvikling.sparesti.exceptions.InvalidCredentialsException;
import idatt2106.systemutvikling.sparesti.exceptions.InvalidTokenException;
import idatt2106.systemutvikling.sparesti.exceptions.NotFoundInDatabaseException;
import idatt2106.systemutvikling.sparesti.exceptions.OpenAIException;
import idatt2106.systemutvikling.sparesti.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "An unexpected error occurred.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    String message = "User not found.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(OpenAIException.class)
  public ResponseEntity<String> handleOpenAIException(OpenAIException e) {
    HttpStatus status = HttpStatus.BAD_GATEWAY;
    String message = "Could not connect to OpenAI api.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String message = "Invalid token.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String message = "Invalid credentials.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<String> handleConflictException(ConflictException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    String message = "A conflict occurred.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(NotFoundInDatabaseException.class)
  public ResponseEntity<String> handleNotFoundInDatabaseException(NotFoundInDatabaseException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    String message = "Could not find requested object in database.";

    return ResponseEntity.status(status).body(message);
  }

  @ExceptionHandler(BankConnectionErrorException.class)
  public ResponseEntity<String> handleBankConnectionErrorException(BankConnectionErrorException e) {
    HttpStatus status = HttpStatus.BAD_GATEWAY;
    String message = "Not able to connect to bank.";

    return ResponseEntity.status(status).body(message);
  }
}
