package idatt2106.systemutvikling.sparesti.dto;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDTOTest {

  @Test
  @DisplayName("Test getters and setters")
  public void testGetterAndSetter() {
    // Create a sample UserDTO object
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("testUser");
    userDTO.setEmail("test@example.com");
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");
    userDTO.setBirthDate("1990-01-01");
    userDTO.setProfilePictureBase64("base64encodedstring");
    userDTO.setMonthlyIncome(5000L);
    userDTO.setMonthlySavings(1000L);
    userDTO.setMonthlyFixedExpenses(2000L);
    userDTO.setCurrentAccount(3000L);
    userDTO.setSavingsAccount(2000L);
    userDTO.setIsConnectedToBank(true);

    // Test getters
    assertEquals("testUser", userDTO.getUsername());
    assertEquals("test@example.com", userDTO.getEmail());
    assertEquals("John", userDTO.getFirstName());
    assertEquals("Doe", userDTO.getLastName());
    assertEquals("1990-01-01", userDTO.getBirthDate());
    assertEquals("base64encodedstring", userDTO.getProfilePictureBase64());
    assertEquals(5000L, userDTO.getMonthlyIncome());
    assertEquals(1000L, userDTO.getMonthlySavings());
    assertEquals(2000L, userDTO.getMonthlyFixedExpenses());
    assertEquals(3000L, userDTO.getCurrentAccount());
    assertEquals(2000L, userDTO.getSavingsAccount());
    assertEquals(true, userDTO.getIsConnectedToBank());

    // Test setters
    userDTO.setUsername("newUser");
    userDTO.setEmail("new@example.com");
    userDTO.setFirstName("Jane");
    userDTO.setLastName("Doe");
    userDTO.setBirthDate("1995-01-01");
    userDTO.setProfilePictureBase64("newbase64encodedstring");
    userDTO.setMonthlyIncome(6000L);
    userDTO.setMonthlySavings(1500L);
    userDTO.setMonthlyFixedExpenses(2500L);
    userDTO.setCurrentAccount(3500L);
    userDTO.setSavingsAccount(2500L);
    userDTO.setIsConnectedToBank(false);

    assertEquals("newUser", userDTO.getUsername());
    assertEquals("new@example.com", userDTO.getEmail());
    assertEquals("Jane", userDTO.getFirstName());
    assertEquals("Doe", userDTO.getLastName());
    assertEquals("1995-01-01", userDTO.getBirthDate());
    assertEquals("newbase64encodedstring", userDTO.getProfilePictureBase64());
    assertEquals(6000L, userDTO.getMonthlyIncome());
    assertEquals(1500L, userDTO.getMonthlySavings());
    assertEquals(2500L, userDTO.getMonthlyFixedExpenses());
    assertEquals(3500L, userDTO.getCurrentAccount());
    assertEquals(2500L, userDTO.getSavingsAccount());
    assertEquals(false, userDTO.getIsConnectedToBank());
  }

  @Test
  @DisplayName("Test no-args constructor")
  public void testNoArgsConstructor() {
    // Test no-args constructor
    UserDTO userDTO = new UserDTO();

    // Ensure that object is not null
    assertNotNull(userDTO);
  }
}
