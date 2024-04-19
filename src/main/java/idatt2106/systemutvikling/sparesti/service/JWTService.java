package idatt2106.systemutvikling.sparesti.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Service class for JWT.
 */
@Service
public class JWTService {

  private static final Logger LOGGER = LogManager.getLogger(JWTService.class);
  private final SecretsConfig secrets;

  public JWTService(SecretsConfig secrets) {
    this.secrets = secrets;
  }

  /**
   * Method to extract username from token.
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
   * Method to validate token and get the DecodedJWT object.
   * @param token the token to validate
   * @return boolean if the token is valid or not
   */
  private boolean isValidTokenFormat(String token) {
    return token != null && token.startsWith("Bearer ");
  }
}