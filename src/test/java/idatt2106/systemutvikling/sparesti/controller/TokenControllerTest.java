package idatt2106.systemutvikling.sparesti.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import idatt2106.systemutvikling.sparesti.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TokenControllerTest {

  @Mock
  private PasswordService passwordService;

  @Mock
  private SecretsConfig secretsConfig;

  @Mock
  private CustomerServiceInterface customerService;

  @InjectMocks
  private TokenController tokenController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testLogin() {
    String username = "testUser";
    String password = "testPassword";
    UserCredentialsDTO loginRequest = new UserCredentialsDTO();
    loginRequest.setUsername(username);
    loginRequest.setPassword(password);

    when(passwordService.correctPassword(username, password)).thenReturn(true);
    when(customerService.customerExists(username)).thenReturn(true);
    when(customerService.hasTwoAccounts(username)).thenReturn(true);
    when(secretsConfig.getJwt()).thenReturn("secret");

    ResponseEntity<String> response = tokenController.login(loginRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(JWT.create()
            .withSubject(username)
            .withIssuer("SparestiTokenIssuerApp")
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plusMillis(Duration.ofMinutes(6).toMillis()))
            .withClaim("role", "ROLE_COMPLETE")
            .sign(Algorithm.HMAC512("secret")), response.getBody());
  }
}