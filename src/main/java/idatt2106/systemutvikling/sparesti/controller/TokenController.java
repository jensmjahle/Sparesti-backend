package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.model.LoginRequestModel;

import java.util.List;
import java.util.logging.Logger;
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

  //todo get new secret key from environment variable and switch the funnySecrets out
  private static String secretKey = null;

  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(6);

  @Autowired
  public TokenController(PasswordService passwordService) {
    this.passwordService = passwordService;
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

  private String generateToken(final String userId) {
    logger.info("Generating token for user: " + userId + ".");
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512("funnySecret");

    // Fetch the roles of the user
    String role = userService.getRole(userId);

    return JWT.create()
            .withSubject(userId)
            .withIssuer("SparestiTokenIssuerApp")
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
            .withClaim("role", role)
            .sign(hmac512);
  }

}

