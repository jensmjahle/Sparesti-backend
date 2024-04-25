package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import idatt2106.systemutvikling.sparesti.service.JWTService;
import idatt2106.systemutvikling.sparesti.service.PasswordService;
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
  Logger logger = Logger.getLogger(TokenController.class.getName());
  PasswordService passwordService;

  private JWTService jwtService;
  private final UserRepository userRepository;

  @Autowired
  public TokenController(PasswordService passwordService, JWTService jwtService,
                         UserRepository userRepository) {
    this.passwordService = passwordService;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  /**
   * Endpoint for letting the user login. If login is successful, returns a JWT for use with secured endpoints.
   * The user can log in by providing the correct login credentials.
   * A user is considered as logged in when it has a token.
   * @param loginRequest A DTO containing a correct username and password combination. Only the fields "username" and "password" is required.
   * @return A JWT to use with secured endpoints.
   */
  @PostMapping(value = "/login")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> login(final @RequestBody UserCredentialsDTO loginRequest) {

    boolean success = false;

    try {
      if (userRepository.findByUsername(loginRequest.getUsername()) == null) {
        logger.warning("Access denied, wrong credentials: User does not exist.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials: User does not exist.");
      }
    } catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");
    }

    try {
      success = passwordService.correctPassword(loginRequest.getUsername(), loginRequest.getPassword());
    }
    catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");
    }

    if (!success)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");

    String token = jwtService.generateToken(loginRequest.getUsername());

    return ResponseEntity.ok().body(token);
  }

  /**
   * Delete the token for the user.
   */
  @DeleteMapping
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteToken() {

    logger.info("Received request to delete token.");
  }

  /**
   * Refresh the JWT token.
   * @param token the token to be exchanged for a new token to be given to the user
   * @return the refreshed token
   */
  @GetMapping(value = "/refresh")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
    logger.info("Received request to refresh token.");

    try {
      String userid = jwtService.extractUsernameFromToken(token);

      return ResponseEntity.ok().body(jwtService.generateToken(userid));

    } catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");
    }
  }

}

