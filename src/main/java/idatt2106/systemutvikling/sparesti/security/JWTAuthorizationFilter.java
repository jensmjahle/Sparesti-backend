package idatt2106.systemutvikling.sparesti.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);
    DecodedJWT jwt = validateTokenAndGetJwt(token);
    if (jwt == null) {
      // validation failed or token expired
      filterChain.doFilter(request, response);
      return;
    }

    final String username = jwt.getSubject();
    final List<String> roles = jwt.getClaim("roles").asList(String.class);

    // if token is valid, add user details to the authentication context
    List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            authorities);
    SecurityContextHolder.getContext().setAuthentication(auth);

    // then, continue with authenticated user context
    filterChain.doFilter(request, response);
  }


  public DecodedJWT validateTokenAndGetJwt(final String token) {
    // remove quotes from token as it breaks the verification
    String tokenWithoutQuotes = token.replace("\"", "");
    try {
      final Algorithm hmac512 = Algorithm.HMAC512(secrets.getJwt());
      final JWTVerifier verifier = JWT.require(hmac512).build();
      return verifier.verify(tokenWithoutQuotes);
    } catch (final JWTVerificationException verificationEx) {
      LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
      return null;
    }
  }
}