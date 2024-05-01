package idatt2106.systemutvikling.sparesti.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * Service class for JWT.
 */
@Service
public class JWTService {

  private static final Logger LOGGER = LogManager.getLogger(JWTService.class);
  private final SecretsConfig secrets;
  private final CustomerServiceInterface customerService;

  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(6);

  public JWTService(SecretsConfig secrets, CustomerServiceInterface customerService, CustomerServiceInterface customerService1) {
    this.secrets = secrets;
    this.customerService = customerService;
  }

  /**
   * Generate a JWT token for the given user. The token is valid for 6 minutes.
   * The token is signed with the HMAC512 algorithm. The token contains the username and the role of the user.
   * The role is either "complete" or "basic". A user is complete if they have two accounts.
   *
   * @param username the username of the user
   * @return the generated token
   */
  public String generateToken(final String username) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(secrets.getJwt());
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

  /**
   * Method to extract username from token. The token is validated with the HMAC512 algorithm.
   * The token must be in the format "Bearer <token>". If the token is invalid, null is returned.
   * Method extracts the username from the token as the subject.
   *
   * @param token the token to extract username from
   * @return the username if the token is valid, null otherwise
   */
  public String extractUsernameFromToken(String token) {
    if (!isValidTokenFormat(token)) {
      LOGGER.warn("Token format is invalid");
      return null;
    }

    try {
      final Algorithm hmac512 = Algorithm.HMAC512(secrets.getJwt());
      DecodedJWT jwt = JWT.require(hmac512).build().verify(token.substring(7)); // remove "Bearer " from token
      return jwt.getSubject();
    } catch (final JWTVerificationException verificationEx) {
      LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
      return null;
    }
  }

  /**
   * Method to validate token and get the DecodedJWT object. The token is validated with the HMAC512 algorithm.
   * The token must be in the format "Bearer <token>". If the token is invalid, null is returned.
   *
   * @param token the token to validate
   * @return boolean if the token is valid or not
   */
  private boolean isValidTokenFormat(String token) {
    return token != null && token.startsWith("Bearer ");
  }
}