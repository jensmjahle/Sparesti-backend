package idatt2106.systemutvikling.sparesti.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LogManager.getLogger(JWTAuthorizationFilter.class);

  public static final String USER = "USER";
  public static final String ROLE_USER = "ROLE_" + USER;

  private SecretsConfig secrets;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {

    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      LOGGER.info("No Bearer token found in Authorization header. Skipping authentication.");
      filterChain.doFilter(request, response);
      return;
    }

    LOGGER.info("Bearer token found in Authorization header. Proceeding with authentication.");
    String token = header.substring(7);
    DecodedJWT jwt = validateTokenAndGetJwt(token);
    if (jwt == null) {
      LOGGER.warn("JWT token validation failed or token expired. Skipping authentication.");
      filterChain.doFilter(request, response);
      return;
    }
    logger.info("JWT token validation successful. Proceeding with authentication.");

    final String username = jwt.getSubject();
    final String role = jwt.getClaim("role").asString();

    logger.info("Setting user context for user: {}"+ username);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(role)));

    logger.info("Setting user context for user: {}"+ username);

    Map<String, Object> userDetails = new HashMap<>();
    userDetails.put(CurrentUserService.KEY_USERNAME, username);
    auth.setDetails(userDetails);

    logger.info("Setting user context for user: {}"+ username);

    SecurityContextHolder.getContext().setAuthentication(auth);

    // Continue with authenticated user context
    filterChain.doFilter(request, response);
  }

  public DecodedJWT validateTokenAndGetJwt(final String token) {
    // Remove quotes from token as it breaks the verification
    String tokenWithoutQuotes = token.replace("\"", "");
    try {
      final Algorithm hmac512 = Algorithm.HMAC512(secrets.getJwt());
      final JWTVerifier verifier = JWT.require(hmac512).build();
      return verifier.verify(tokenWithoutQuotes);
    } catch (final JWTVerificationException verificationEx) {
      LOGGER.warn("JWT token verification failed: {}", verificationEx.getMessage());
      return null;
    }
  }
}
