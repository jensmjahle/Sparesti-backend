package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.model.LoginRequestModel;

import java.util.List;
import java.util.logging.Logger;

import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import idatt2106.systemutvikling.sparesti.security.JWTAuthorizationFilter;
import idatt2106.systemutvikling.sparesti.service.PasswordService;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "/placeholder")
@EnableAutoConfiguration
@CrossOrigin(origins = "*") //todo change to frontend url
public class TokenController {
  Logger logger = Logger.getLogger(TokenController.class.getName());
  PasswordService passwordService;

  private final CustomerServiceInterface customerService;

  //todo get new secret key from environment variable and switch the funnySecrets out
  private static String secretKey = null;

  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(6);

  @Autowired
  public TokenController(PasswordService passwordService, CustomerServiceInterface customerService) {
    this.passwordService = passwordService;
      this.customerService = customerService;
  }

  @PostMapping(value = "/2Placeholder")
  @ResponseStatus(value = HttpStatus.CREATED)
  public String generateToken(final @RequestBody LoginRequestModel loginRequest) {
    logger.info("Received request to generate token for user: " + loginRequest.getUsername() + ".");
    if (passwordService.correctPassword(loginRequest.getUsername(), loginRequest.getPassword())) {
      String generatedToken = generateToken(loginRequest.getUsername());
      return generatedToken;
    }
    logger.warning("Access denied, wrong credentials....");

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials....");
  }

  @PostMapping(value = "/3Placeholder")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteToken() {

    logger.info("Received request to delete token.");
    secretKey = null;
  }

  private String generateToken(final String username) {
    logger.info("Generating token for user: " + username + ".");
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512("funnySecret");

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

