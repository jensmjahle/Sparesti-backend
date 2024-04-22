package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.security.JWTAuthorizationFilter;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import idatt2106.systemutvikling.sparesti.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TokenControllerTest {

  @Mock
  private SecretsConfig secretsConfig;

  @InjectMocks
  private TokenController tokenController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(secretsConfig.getJwt()).thenReturn("DefaultJWTSecret"); // replace "your_secret_key" with your actual secret key

    // Mock Instant.now()
    Instant fixedInstant = Instant.now();
    try (MockedStatic<Instant> mocked = Mockito.mockStatic(Instant.class)) {
      mocked.when(Instant::now).thenReturn(fixedInstant);
    }
  }

  @Test
  public void testGenerateTokenAndValidate() throws Exception {
    // Create an instance of JWTAuthorizationFilter
    JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(secretsConfig);

    // Use reflection to access the generateToken method
    Method generateTokenMethod = TokenController.class.getDeclaredMethod("generateToken", String.class);
    generateTokenMethod.setAccessible(true);

    // Generate a token
    String username = "testUser";
    String token = (String) generateTokenMethod.invoke(tokenController, username);

    // Validate the token and get the JWT
    DecodedJWT jwt = jwtAuthorizationFilter.validateTokenAndGetJwt(token);

    // Perform your assertions
    assertNotNull(jwt);
    assertEquals(username, jwt.getSubject());
    assertEquals("ROLE_BASIC", jwt.getClaim("role").asString()); // expect "ROLE_BASIC" instead of "ROLE_COMPLETE"
  }
}