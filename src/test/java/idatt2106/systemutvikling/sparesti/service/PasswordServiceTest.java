package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PasswordServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private PasswordService passwordService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    passwordEncoder = Mockito.mock(PasswordEncoder.class);
    ReflectionTestUtils.setField(passwordService, "passwordEncoder", passwordEncoder);
  }

  @Test
  public void testCorrectPassword() {
    String username = "testUser";
    String password = "testPassword";
    UserDAO user = new UserDAO();
    user.setUsername(username);
    user.setPassword(password);

    when(userRepository.findByUsername(username)).thenReturn(user);
    when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

    boolean result = passwordService.correctPassword(username, password);

    assertTrue(result);
  }
}