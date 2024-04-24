package idatt2106.systemutvikling.sparesti.service;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class CurrentUserServiceTest {

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @BeforeEach
  public void tearDown() {
    SecurityContextHolder.clearContext();
  }

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  public void testIsCompleteUser() {
// Arrange
    String role = SecurityConfig.ROLE_COMPLETE;
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    when(authentication.getAuthorities()).thenReturn((Collection) authorities);
// Act
    boolean isCompleteUser = CurrentUserService.isCompleteUser();
// Assert
    assertTrue(isCompleteUser);
  }
}