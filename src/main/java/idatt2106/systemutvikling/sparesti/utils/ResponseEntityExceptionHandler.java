package idatt2106.systemutvikling.sparesti.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityExceptionHandler {

  private ResponseEntityExceptionHandler() {
    // Empty constructor
  }

  public static ResponseEntity<Object> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    switch (e.getClass().getSimpleName()) {
      case "UserNotFoundException" -> status = HttpStatus.NOT_FOUND;
      case "IllegalArgumentException", "IllegalStateException" -> status = HttpStatus.BAD_REQUEST;
      case "OpenAIException" -> status = HttpStatus.BAD_GATEWAY;
      case "InvalidTokenException", "InvalidCredentialsException" ->
          status = HttpStatus.UNAUTHORIZED;

    }
    return ResponseEntity.status(status).body(e.getMessage());
  }

}
