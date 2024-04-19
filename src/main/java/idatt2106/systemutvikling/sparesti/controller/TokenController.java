package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
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

  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(6);

  @Autowired
  public TokenController(PasswordService passwordService, SecretsConfig secretsConfig, CustomerServiceInterface customerService) {
    this.passwordService = passwordService;
    this.secretsConfig = secretsConfig;
    this.customerService = customerService;
  }

  /**
   * Endpoint for letting the user login. If login is successful, returns a JWT for use with secured endpoints.
   * The user can login by providing the correct login credentials.
   * A user is considered as logged in when it has a token.
   * @param loginRequest A DTO containing a correct username and password combination. Only the fields "username" and "password" is required.
   * @return A JWT to use with secured endpoints.
   */
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<String> login(final @RequestBody UserCredentialsDTO loginRequest) {
    boolean success = passwordService.correctPassword(loginRequest.getUsername(), loginRequest.getPassword());

    if (!success)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, wrong credentials");

    String token = generateToken(loginRequest.getUsername());

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
   * Generate a JWT token for the given user.
   * @param username the username of the user
   * @return the generated token
   */
  private String generateToken(final String username) {
    logger.info("Generating token for user: " + username + ".");
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(secretsConfig.getJwt());

    boolean isCompleteUser = true;
    isCompleteUser &= customerService.customerExists(username);
    isCompleteUser &= customerService.hasTwoAccounts(username);

    String role = isCompleteUser ? SecurityConfig.ROLE_COMPLETE : SecurityConfig.ROLE_BASIC;

    return JWT.create()
            .withSubject(username)
            .withIssuer("SparestiTokenIssuerApp")
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
            .withClaim("role", role)
            .sign(hmac512);
  }

}

