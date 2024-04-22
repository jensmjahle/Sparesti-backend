package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
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
@CrossOrigin(origins = "*") //todo change to frontend url
public class TokenController {
  Logger logger = Logger.getLogger(TokenController.class.getName());
  PasswordService passwordService;

  private final SecretsConfig secretsConfig;

  private final CustomerServiceInterface customerService;

  private JWTService jwtService;

  @Autowired
  public TokenController(PasswordService passwordService, SecretsConfig secretsConfig, CustomerServiceInterface customerService, JWTService jwtService) {
    this.passwordService = passwordService;
    this.secretsConfig = secretsConfig;
    this.customerService = customerService;
    this.jwtService = jwtService;
  }

  /**
   * Endpoint for letting the user login. If login is successful, returns a JWT for use with secured endpoints.
   * The user can login by providing the correct login credentials.
   * A user is considered as logged in when it has a token.
   * @param loginRequest A DTO containing a correct username and password combination. Only the fields "username" and "password" is required.
   * @return A JWT to use with secured endpoints.
   */
  @PostMapping(value = "/login")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> login(final @RequestBody UserCredentialsDTO loginRequest) {

    boolean success = false;

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
      final Algorithm hmac512 = Algorithm.HMAC512(secretsConfig.getJwt());
      String userid = jwtService.extractUsernameFromToken(token);

      return ResponseEntity.ok().body(jwtService.generateToken(userid));

    } catch (Exception e) {
      logger.warning("Access denied, wrong credentials: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");
    }
  }

}

