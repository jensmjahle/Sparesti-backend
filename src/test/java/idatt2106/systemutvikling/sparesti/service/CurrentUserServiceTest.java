package idatt2106.systemutvikling.sparesti.service;
<<<<<<< HEAD

=======
>>>>>>> 8ce3200399e0e8394a4ef9eb2cab63547fcd6fe4
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
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.Collection;

=======
import java.util.ArrayList;
import java.util.Collection;
>>>>>>> 8ce3200399e0e8394a4ef9eb2cab63547fcd6fe4
import static org.junit.Assert.assertTrue;
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
  public void testIsCompleteUser() {
<<<<<<< HEAD
    // Arrange
    String role = SecurityConfig.ROLE_COMPLETE;
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));

    when(authentication.getAuthorities()).thenReturn((Collection) authorities);

    // Act
    boolean isCompleteUser = CurrentUserService.isCompleteUser();

    // Assert
=======
// Arrange
    String role = SecurityConfig.ROLE_COMPLETE;
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    when(authentication.getAuthorities()).thenReturn((Collection) authorities);
// Act
    boolean isCompleteUser = CurrentUserService.isCompleteUser();
// Assert
>>>>>>> 8ce3200399e0e8394a4ef9eb2cab63547fcd6fe4
    assertTrue(isCompleteUser);
  }
}