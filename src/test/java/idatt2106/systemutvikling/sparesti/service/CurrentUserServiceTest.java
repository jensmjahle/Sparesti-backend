package idatt2106.systemutvikling.sparesti.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CurrentUserServiceTest {

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  public void testGetCurrentUsername() {
    String expectedUsername = "testUser";
    Map<String, Object> details = new HashMap<>();
    details.put(CurrentUserService.KEY_USERNAME, expectedUsername);

    when(authentication.getDetails()).thenReturn(details);

    String actualUsername = CurrentUserService.getCurrentUsername();

    assertEquals(expectedUsername, actualUsername);
  }
}