package idatt2106.systemutvikling.sparesti.handler;

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


}
