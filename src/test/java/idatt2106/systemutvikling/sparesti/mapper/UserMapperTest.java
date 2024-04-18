package idatt2106.systemutvikling.sparesti.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

  @Test
  @DisplayName("Test UserDAO to UserDTO mapping")
  public void testToUserDTO() {
    // Create a UserDAO object with sample data
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    userDAO.setEmail("test@example.com");
    userDAO.setFirstName("John");
    userDAO.setLastName("Doe");
    userDAO.setBirthDate(LocalDate.of(1990, 5, 15));
    userDAO.setProfilePicture(new byte[0]); // Assuming an empty byte array for simplicity
    userDAO.setMonthlyIncome(5000L);
    userDAO.setMonthlySavings(1000L);
    userDAO.setMonthlyFixedExpenses(2000L);
    userDAO.setCurrentAccount(500L);
    userDAO.setSavingsAccount(2000L);

    // Map the UserDAO object to a UserDTO object
    UserDTO userDTO = UserMapper.toUserDTO(userDAO);

    // Verify that the mapping is correct
    assertNotNull(userDTO);
    assertEquals("testUser", userDTO.getUsername());
    assertEquals("test@example.com", userDTO.getEmail());
    assertEquals("John", userDTO.getFirstName());
    assertEquals("Doe", userDTO.getLastName());
    assertEquals(LocalDate.of(1990, 5, 15), userDTO.getBirthDate());
    // Assuming the profile picture is converted to Base64, verify the Base64 string
    assertEquals("", userDTO.getProfilePictureBase64());
    assertEquals(5000L, userDTO.getMonthlyIncome());
    assertEquals(1000L, userDTO.getMonthlySavings());
    assertEquals(2000L, userDTO.getMonthlyFixedExpenses());
    assertEquals(500L, userDTO.getCurrentAccount());
    assertEquals(2000L, userDTO.getSavingsAccount());
  }

  @Test
  void testUserCredentialsDTOToUserDAO() {
    // Create a UserCredentialsDTO instance with some sample data
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setUsername("testUser");
    userCredentialsDTO.setPassword("testPassword");
    userCredentialsDTO.setEmail("test@example.com");
    userCredentialsDTO.setFirstName("John");
    userCredentialsDTO.setLastName("Doe");
    userCredentialsDTO.setBirthDate(LocalDate.of(1990, 5, 15));

    // Map UserCredentialsDTO to UserDAO
    UserDAO userDAO = UserMapper.userCredentialsDTOToUserDAO(userCredentialsDTO);

    // Verify the mapping
    assertEquals("testUser", userDAO.getUsername());
    assertEquals("testPassword", userDAO.getPassword());
    assertEquals("test@example.com", userDAO.getEmail());
    assertEquals("John", userDAO.getFirstName());
    assertEquals("Doe", userDAO.getLastName());
    assertEquals(LocalDate.of(1990, 5, 15), userDAO.getBirthDate());
  }

  @Test
  void testUserDTOToUserDAO() throws IOException {
    // Create a UserDTO instance with some sample data
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("testUser");
    userDTO.setEmail("test@example.com");
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");
    userDTO.setBirthDate(LocalDate.of(1990, 5, 15));
    Path imagePath = Paths.get("src/test/java/testResources/testImage.jpeg");
    byte[] imageBytes = Files.readAllBytes(imagePath);

    // Convert the byte array to a base64 string
    String base64String = Base64Mapper.toBase64String(imageBytes);
    userDTO.setProfilePictureBase64(base64String);
    userDTO.setMonthlyIncome(5000L);
    userDTO.setMonthlySavings(1000L);
    userDTO.setMonthlyFixedExpenses(200L);
    userDTO.setCurrentAccount(123456789L);
    userDTO.setSavingsAccount(987654321L);

    // Map UserDTO to UserDAO
    UserDAO userDAO = UserMapper.userDTOToUserDAO(userDTO);

    // Verify the mapping
    assertEquals("testUser", userDAO.getUsername());
    assertEquals("test@example.com", userDAO.getEmail());
    assertEquals("John", userDAO.getFirstName());
    assertEquals("Doe", userDAO.getLastName());
    assertEquals(LocalDate.of(1990, 5, 15), userDAO.getBirthDate());
    assertArrayEquals(imageBytes, userDAO.getProfilePicture());
    assertEquals(5000L, userDAO.getMonthlyIncome());
    assertEquals(1000L, userDAO.getMonthlySavings());
    assertEquals(200L, userDAO.getMonthlyFixedExpenses());
    assertEquals(123456789L, userDAO.getCurrentAccount());
    assertEquals(987654321L, userDAO.getSavingsAccount());
  }
}
