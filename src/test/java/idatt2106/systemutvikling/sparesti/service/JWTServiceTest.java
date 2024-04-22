package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceTest {

  @Mock
  private SecretsConfig secretsConfig;

  @Mock
  private CustomerServiceInterface customerService;

  @InjectMocks
  private JWTService jwtService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(secretsConfig.getJwt()).thenReturn("secret");
    when(customerService.customerExists(anyString())).thenReturn(true);
    when(customerService.hasTwoAccounts(anyString())).thenReturn(true);
  }

  @Test
  void generateToken() {
    String token = jwtService.generateToken("testUser");
    assertNotNull(token);
  }

  @Test
  void extractUsernameFromToken() {
    String token = jwtService.generateToken("testUser");
    String username = jwtService.extractUsernameFromToken("Bearer " + token);
    assertEquals("testUser", username);
  }
}