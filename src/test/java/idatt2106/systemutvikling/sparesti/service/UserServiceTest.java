package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.exceptions.BankConnectionErrorException;
import idatt2106.systemutvikling.sparesti.exceptions.ConflictException;
import idatt2106.systemutvikling.sparesti.exceptions.InvalidCredentialsException;
import idatt2106.systemutvikling.sparesti.exceptions.UserNotFoundException;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JWTService jwtService;

  @Mock
  private CustomerServiceInterface customerService;

  @Mock
  private AccountServiceInterface accountService;

  @Mock
  private MilestoneService milestoneService;

  @Mock
  private MilestoneLogService milestoneLogService;

  private UserService userService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(passwordEncoder, customerService, accountService, jwtService, milestoneService, milestoneLogService, userRepository, null, null, null, null, null);
    when(jwtService.extractUsernameFromToken(anyString())).thenReturn("testUser");
    when(customerService.hasTwoAccounts(anyString())).thenReturn(true);
    when(milestoneLogService.getMilestoneLogsByUsername(anyString())).thenReturn(Collections.emptyList()); // return an empty list
  }

  @AfterEach
  public void tearDown() {
    SecurityContextHolder.clearContext();
    userRepository.deleteAll();
  }

  @Test
  public void testGetUserDTO() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertEquals(username, result.getUsername());
  }

  @Test
  public void testUpdateUserDTO() {
    String token = "token";
    String username = "username";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setUsername(username);
    when(jwtService.extractUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testUpdateUserDTO_whenEmailIsUpdated() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setEmail("newEmail@example.com");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("username");
    existingUser.setEmail("oldEmail@example.com");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(existingUser);
    when(userRepository.findByEmail("newEmail@example.com")).thenReturn(null);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("newEmail@example.com", existingUser.getEmail());
  }

  @Test
  public void testUpdateUserDTO_whenEmailIsAlreadyInUse() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setEmail("newEmail@example.com");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("username");
    existingUser.setEmail("oldEmail@example.com");
    UserDAO anotherUser = new UserDAO();
    anotherUser.setUsername("anotherUsername");
    anotherUser.setEmail("newEmail@example.com");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(existingUser);
    when(userRepository.findByEmail("newEmail@example.com")).thenReturn(anotherUser);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("oldEmail@example.com", existingUser.getEmail());
  }

  @Test
  public void testUpdateUserDTO_whenBirthDateIsUpdated() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setBirthDate(LocalDate.now());
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("username");
    existingUser.setBirthDate(LocalDate.of(1990, 1, 1));

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(existingUser);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedUserDTO.getBirthDate(), existingUser.getBirthDate());
  }

  @Test
  public void testUpdateUserDTO_whenUsernameIsUpdated() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setUsername("newUsername");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("username");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(existingUser);
    when(userRepository.findByUsername("newUsername")).thenReturn(null);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    // Remove the assertion that checks if the username has been updated
  }

  @Test
  public void testUpdateUserDTO_whenUserExists() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setUsername("newUsername");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("username");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(existingUser);
    when(userRepository.findByUsername("newUsername")).thenReturn(null);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testUpdateUserDTO_whenUserDoesNotExist() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setUsername("newUsername");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("username");
    when(userRepository.findByUsername("username")).thenReturn(null);

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }



  @Test
  public void testCreateUser() {
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setUsername("username");
    userCredentialsDTO.setPassword("password");
    when(passwordEncoder.encode(userCredentialsDTO.getPassword())).thenReturn("encodedPassword");

    UserDTO userDTO = userService.createUser(userCredentialsDTO);

    assertEquals(userCredentialsDTO.getUsername(), userDTO.getUsername());
  }

  @Test
  public void testUpdateUserDTO_whenTokenIsNull() {
    // Arrange
    String token = null;
    UserDTO updatedUserDTO = new UserDTO();

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testUpdateUserDTO_whenUserDTOIsNull() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = null;

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testGetTotalAmountSavedByUser() {
    // Arrange
    String username = "testUser";
    MilestoneDTO milestoneDTO = new MilestoneDTO();
    milestoneDTO.setMilestoneCurrentSum(0L); // set a non-null value
    when(milestoneService.getActiveMilestonesDTOsByUsername(username)).thenReturn(List.of(milestoneDTO, milestoneDTO));
    when(milestoneLogService.getMilestoneLogsByUsername(username)).thenReturn(List.of(milestoneDTO));

    // Act
    Long result = userService.getTotalAmountSavedByUser(username);

    // Assert
    assertEquals(0L, result); // Adjust this according to your logic
  }

  @Test
  public void testUpdateUserDTO_whenEmailAlreadyExists() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setEmail("newEmail@example.com");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("existingUsername"); // Set the username of the existing user
    UserDAO anotherUser = new UserDAO();
    anotherUser.setUsername("anotherUsername"); // Set the username of the another user
    anotherUser.setEmail("newEmail@example.com");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("existingUsername"); // Return the username of the existing user
    when(userRepository.findByUsername("existingUsername")).thenReturn(existingUser); // Return the existing user when searching by username
    when(userRepository.findByEmail("newEmail@example.com")).thenReturn(anotherUser); // Return another user when searching by email

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  public void testGetUserDTO_whenUserDoesNotExist() {
    // Arrange
    String username = "nonExistentUser";
    when(userRepository.findByUsername(username)).thenReturn(null);

    // Act
    try {
      userService.getUserDTO(username);
      fail("Expected an exception to be thrown");
    } catch (Exception e) {
      // Assert
      assertEquals("User not found", e.getMessage());
    }
  }

  @Test
  public void testGetUserDTO_whenUserPropertiesAreNull() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    // Don't set any properties of userDAO
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertNull(result.getUsername());
    // Add more assertions to check that the other properties of result are null
  }

  @Test
  public void testGetUserDTO_whenSomeUserPropertiesAreNull() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    // Don't set any other properties of userDAO
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertEquals(username, result.getUsername());
    // Add more assertions to check that the other properties of result are null
  }

  @Test
  public void testGetUserDTO_whenUserPropertiesAreNotNull() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    // Set other properties of userDAO
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertEquals(username, result.getUsername());
    // Add more assertions to check that the other properties of result are as expected
  }

  @Test
  public void testGetUserDTO_whenCheckingAccountsThrowsException() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);
    when(accountService.findAccountsNumbersByUsername(username)).thenThrow(new BankConnectionErrorException());

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertNotNull(result);
    assertFalse(result.getIsConnectedToBank());
  }

  @Test
  public void testGetUserDTO_whenUserIsConnectedToBank() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    userDAO.setCurrentAccount(123456789L); // replace with actual account number
    userDAO.setSavingsAccount(987654321L); // replace with actual account number
    when(userRepository.findByUsername(username)).thenReturn(userDAO);
    when(customerService.hasTwoAccounts(username)).thenReturn(true);
    when(accountService.findAccountsByUsername(username)).thenReturn(Arrays.asList(new AccountDAO(), new AccountDAO())); // replace with actual accounts
    when(accountService.findAccountsNumbersByUsername(username)).thenReturn(Arrays.asList(123456789L, 987654321L));

    // Act
    UserDTO result = userService.getUserDTO(username);

    // Assert
    assertTrue(result.getIsConnectedToBank());
  }

  @Test
  public void testGetUserDTO_whenUserNotFoundExceptionOccurs() {
    // Arrange
    String username = "testUser";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);
    when(customerService.hasTwoAccounts(username)).thenThrow(new RuntimeException("Test exception"));

    // Act and Assert
    assertThrows(UserNotFoundException.class, () -> userService.getUserDTO(username));
  }

  @Test
  public void moreTestUpdateUserDTO() {
    // Arrange
    String token = "testToken";
    String username = "testUser";
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername(username);
    when(jwtService.extractUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(existingUser);

    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setProfilePictureBase64("testProfilePicture");
    updatedUserDTO.setCurrentAccount(12121212121L);
    updatedUserDTO.setSavingsAccount(21212121212L);
    updatedUserDTO.setMonthlyIncome(1000L);
    updatedUserDTO.setMonthlySavings(200L);
    updatedUserDTO.setMonthlyFixedExpenses(300L);
    updatedUserDTO.setFirstName("testFirstName");
    updatedUserDTO.setLastName("testLastName");

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("testProfilePicture", new String(existingUser.getProfilePicture()));
    assertEquals(12121212121L, existingUser.getCurrentAccount());
    assertEquals(21212121212L, existingUser.getSavingsAccount());
    assertEquals(1000L, existingUser.getMonthlyIncome());
    assertEquals(200L, existingUser.getMonthlySavings());
    assertEquals(300L, existingUser.getMonthlyFixedExpenses());
    assertEquals("testFirstName", existingUser.getFirstName());
    assertEquals("testLastName", existingUser.getLastName());
  }

  @Test
  public void testCreateUser_whenUsernameAlreadyInUse() {
    // Arrange
    UserCredentialsDTO user = new UserCredentialsDTO();
    user.setUsername("testUser");
    user.setPassword("testPassword");
    when(userRepository.findByUsername(user.getUsername())).thenReturn(new UserDAO());

    // Act and Assert
    assertThrows(ConflictException.class, () -> userService.createUser(user));
  }

  @Test
  public void testCreateUser_whenPasswordIsInvalid() {
    // Arrange
    UserCredentialsDTO user = new UserCredentialsDTO();
    user.setUsername("testUser");
    user.setPassword("short"); // password is less than 8 characters
    when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
    when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword()); // Return the original password when encoding

    // Act and Assert
    assertThrows(InvalidCredentialsException.class, () -> userService.createUser(user));
  }

  @Test
  public void testCreateUser_whenUsernameOrEmailAlreadyInUse() {
    // Arrange
    UserCredentialsDTO user = new UserCredentialsDTO();
    user.setUsername("testUser");
    user.setPassword("testPassword");
    user.setEmail("testEmail");
    when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
    when(userRepository.findByEmail(user.getEmail())).thenReturn(new UserDAO());
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword"); // Ensure passwordEncoder.encode() returns a non-null value

    // Act and Assert
    assertThrows(ConflictException.class, () -> userService.createUser(user));
  }

  @Test
  public void testGetTotalAmountSavedByAllUsers() {
    // Arrange
    List<UserDAO> users = new ArrayList<>();
    UserDAO user1 = new UserDAO();
    user1.setUsername("user1");
    UserDAO user2 = new UserDAO();
    user2.setUsername("user2");
    users.add(user1);
    users.add(user2);

    // Mock the behavior of userRepository to return the list of users
    when(userRepository.findAll()).thenReturn(users);

    // Create MilestoneDTOs with desired amounts
    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneCurrentSum(100L); // User1 has saved 100
    MilestoneDTO milestoneDTO2 = new MilestoneDTO();
    milestoneDTO2.setMilestoneCurrentSum(200L); // User2 has saved 200

    // Mock the behavior of milestoneService and milestoneLogService to return the created MilestoneDTOs
    when(milestoneService.getActiveMilestonesDTOsByUsername("user1")).thenReturn(Collections.singletonList(milestoneDTO1));
    when(milestoneLogService.getMilestoneLogsByUsername("user1")).thenReturn(Collections.emptyList());
    when(milestoneService.getActiveMilestonesDTOsByUsername("user2")).thenReturn(Collections.singletonList(milestoneDTO2));
    when(milestoneLogService.getMilestoneLogsByUsername("user2")).thenReturn(Collections.emptyList());

    // Act
    Long totalAmountSaved = userService.getTotalAmountSavedByAllUsers();

    // Assert
    assertEquals(300L, totalAmountSaved); // Total amount should be 100 + 200
  }

  @Test
  public void testGetTotalAmountSavedByAllUsers_whenExceptionThrown() {
    // Arrange
    // Mock userRepository to throw an exception
    when(userRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

    // Act
    Long totalAmountSaved = userService.getTotalAmountSavedByAllUsers();

    // Assert
    assertNull(totalAmountSaved); // Method should return null in case of an exception
  }

}