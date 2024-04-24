package idatt2106.systemutvikling.sparesti.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class JWTAuthorizationFilterTest {

  @Mock
  private SecretsConfig secretsConfig;

  @InjectMocks
  private JWTAuthorizationFilter jwtAuthorizationFilter;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

<<<<<<< HEAD
  @BeforeEach
  public void tearDown() {
    SecurityContextHolder.clearContext();
  }
=======
>>>>>>> 8ce3200399e0e8394a4ef9eb2cab63547fcd6fe4

  @Test
  public void testDoFilterInternal() throws Exception {
    String username = "testUser";
    String role = "ROLE_USER";
    String secret = "secret";
    String token = JWT.create()
            .withSubject(username)
            .withClaim("role", role)
            .sign(Algorithm.HMAC512(secret));

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain filterChain = new MockFilterChain();

    when(secretsConfig.getJwt()).thenReturn(secret);

    jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
<<<<<<< HEAD
    System.out.println( SecurityContextHolder.getContext().getAuthentication().getName());
    System.out.println(username);
    assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
=======

    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication auth = securityContext.getAuthentication();

    assertEquals(username, auth.getName());
>>>>>>> 8ce3200399e0e8394a4ef9eb2cab63547fcd6fe4
  }
  //*/
}