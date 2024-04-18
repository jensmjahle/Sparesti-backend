package idatt2106.systemutvikling.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserCredentialsDTOTest {

  @Test
  @DisplayName("Test getters and setters")
  public void testGetterAndSetter() {
    // Create a sample UserCredentialsDTO object
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setUsername("testUser");
    userCredentialsDTO.setPassword("password");
    userCredentialsDTO.setEmail("test@example.com");
    userCredentialsDTO.setFirstName("John");
    userCredentialsDTO.setLastName("Doe");
    userCredentialsDTO.setBirthDate("1990-01-01");

    // Test getters
    assertEquals("testUser", userCredentialsDTO.getUsername());
    assertEquals("password", userCredentialsDTO.getPassword());
    assertEquals("test@example.com", userCredentialsDTO.getEmail());
    assertEquals("John", userCredentialsDTO.getFirstName());
    assertEquals("Doe", userCredentialsDTO.getLastName());
    assertEquals("1990-01-01", userCredentialsDTO.getBirthDate());

    // Test setters
    userCredentialsDTO.setUsername("newUser");
    userCredentialsDTO.setPassword("newPassword");
    userCredentialsDTO.setEmail("new@example.com");
    userCredentialsDTO.setFirstName("Jane");
    userCredentialsDTO.setLastName("Doe");
    userCredentialsDTO.setBirthDate("1995-01-01");

    assertEquals("newUser", userCredentialsDTO.getUsername());
    assertEquals("newPassword", userCredentialsDTO.getPassword());
    assertEquals("new@example.com", userCredentialsDTO.getEmail());
    assertEquals("Jane", userCredentialsDTO.getFirstName());
    assertEquals("Doe", userCredentialsDTO.getLastName());
    assertEquals("1995-01-01", userCredentialsDTO.getBirthDate());
  }

  @Test
  @DisplayName("Test no-args constructor")
  public void testNoArgsConstructor() {
    // Test no-args constructor
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();

    // Ensure that object is not null
    assertNotNull(userCredentialsDTO);
  }
}
