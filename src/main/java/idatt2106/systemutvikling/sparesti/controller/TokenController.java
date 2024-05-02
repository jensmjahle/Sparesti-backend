package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import idatt2106.systemutvikling.sparesti.service.JWTService;
import idatt2106.systemutvikling.sparesti.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/auth")
@EnableAutoConfiguration
public class TokenController {

  private final UserRepository userRepository;
  Logger logger = Logger.getLogger(TokenController.class.getName());
  PasswordService passwordService;
  private JWTService jwtService;

  @Autowired
  public TokenController(PasswordService passwordService, JWTService jwtService,
                         UserRepository userRepository) {
    this.passwordService = passwordService;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Operation(
          summary = "Login",
          description = "Login with username and password"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "201",
                  description = "Login successful",
                  content = {
                          @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = String.class))
                  }
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Access denied, wrong credentials",
                  content = @Content
          )
  })
  @PostMapping(value = "/login")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> login(final @RequestBody UserCredentialsDTO loginRequest) {

    boolean success = false;

    try {
      if (userRepository.findByUsername(loginRequest.getUsername()) == null) {
        logger.warning("Access denied, wrong credentials: User does not exist.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Access denied, wrong credentials: User does not exist.");
      }
    } catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body("Access denied, wrong credentials");
    }

    try {
      success = passwordService.correctPassword(loginRequest.getUsername(),
              loginRequest.getPassword());
    } catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body("Access denied, wrong credentials");
    }

    if (!success) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body("Access denied, wrong credentials");
    }

    String token = jwtService.generateToken(loginRequest.getUsername());

    return ResponseEntity.ok().body(token);
  }

  @Operation(
          summary = "Refresh token",
          description = "Refresh"
  )
  @ApiResponse(
          responseCode = "201",
          description = "Token refreshed",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = String.class))
          }
  )
  @GetMapping(value = "/refresh")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> refreshToken() {
    logger.info("Received request to refresh token.");

    return ResponseEntity.ok().body(jwtService.generateToken(CurrentUserService.getCurrentUsername()));
  }
}

