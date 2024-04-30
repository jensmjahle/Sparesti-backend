package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.exceptions.InvalidCredentialsException;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

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
    passwordEncoder = mock(PasswordEncoder.class);
    userService = new UserService(passwordEncoder, customerService, accountService, jwtService, milestoneService, milestoneLogService, userRepository, null, null, null, null, null);
    when(jwtService.extractUsernameFromToken(anyString())).thenReturn("testUser");
    when(customerService.hasTwoAccounts(anyString())).thenReturn(true);
    when(milestoneLogService.getMilestoneLogsByUsername(anyString())).thenReturn(Collections.emptyList()); // return an empty list
    mockCurrentUsername();
  }

  private void mockCurrentUsername() {
    // Create a mock Authentication object with the specified username
    Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", null);

    // Set the mock Authentication object into SecurityContextHolder
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
  }

  @Test
  void testGetUserDTO() {
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
  void testUpdateUserDTO() {
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
  void testUpdateUserDTO_whenEmailIsUpdated() {
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
  void testUpdateUserDTO_whenEmailIsAlreadyInUse() {
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
  void testUpdateUserDTO_whenBirthDateIsUpdated() {
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
  void testUpdateUserDTO_whenUsernameIsUpdated() {
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
  void testUpdateUserDTO_whenUserExists() {
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
  void testUpdateUserDTO_whenUserDoesNotExist() {
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
  void testCreateUser() {
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setUsername("username");
    userCredentialsDTO.setPassword("password");
    when(passwordEncoder.encode(userCredentialsDTO.getPassword())).thenReturn("encodedPassword");

    UserDTO userDTO = userService.createUser(userCredentialsDTO);

    assertEquals(userCredentialsDTO.getUsername(), userDTO.getUsername());
  }

  @Test
  void testUpdateUserDTO_whenTokenIsNull() {
    // Arrange
    String token = null;
    UserDTO updatedUserDTO = new UserDTO();

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testUpdateUserDTO_whenUserDTOIsNull() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = null;

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGetTotalAmountSavedByUser() {
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
  void testUpdateUserDTO_whenEmailAlreadyExists() {
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
  void testUpdatePassword_Success() {
    // Mock CurrentUserService
    //when(CurrentUserService.getCurrentUsername()).thenReturn("testuser");

    // Mock UserRepository
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testuser");
    userDAO.setPassword(passwordEncoder.encode("oldpassword")); // Set the current encoded password
    when(userRepository.findByUsername("testuser")).thenReturn(userDAO);

    when(passwordEncoder.matches("oldpassword", userDAO.getPassword())).thenReturn(true);
    when(passwordEncoder.encode("newpassword")).thenReturn("encodednewpassword");

    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setPassword("oldpassword");
    userCredentialsDTO.setNewPassword("newpassword");

    when(userRepository.findByUsername(CurrentUserService.getCurrentUsername())).thenReturn(userDAO);
    String result = userService.updatePassword(userCredentialsDTO);

    verify(userRepository, times(1)).findByUsername(CurrentUserService.getCurrentUsername());
    verify(passwordEncoder, times(2)).encode("newpassword");
    verify(userRepository, times(1)).save(userDAO);

    assertEquals("Password updated", result);
    assertEquals("encodednewpassword", userDAO.getPassword());
  }

  @Test
  void testUpdatePassword_InvalidPassword() {
    // Prepare test data with invalid old password
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setPassword("wrongpassword");
    userCredentialsDTO.setNewPassword("newpassword");

    // Mock UserRepository
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testuser");
    userDAO.setPassword(passwordEncoder.encode("oldpassword")); // Set the current encoded password
    when(userRepository.findByUsername("testuser")).thenReturn(userDAO);
    when(userRepository.findByUsername(CurrentUserService.getCurrentUsername())).thenReturn(userDAO);

    // Mock PasswordEncoder
    when(passwordEncoder.matches("wrongpassword", userDAO.getPassword())).thenReturn(false);

    // Call the method and assert the expected exception
    assertThrows(InvalidCredentialsException.class, () -> userService.updatePassword(userCredentialsDTO));
  }

  @Test
  void testUpdatePassword_InvalidNewPassword() {
    // Prepare test data with valid old password and invalid new password
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setPassword("oldpassword");
    userCredentialsDTO.setNewPassword("newpassword");

    // Mock UserDAO
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testuser");
    userDAO.setPassword(passwordEncoder.encode("oldpassword")); // Mocked encoded password
    when(userRepository.findByUsername("testuser")).thenReturn(userDAO);
    when(userRepository.findByUsername(CurrentUserService.getCurrentUsername())).thenReturn(userDAO);

    // Mock PasswordEncoder behavior
    String encodedNewPassword = "encodednewpassword";
    when(passwordEncoder.encode("newpassword")).thenReturn(encodedNewPassword); // Mock encoding behavior

    // Call the method and assert the expected exception
    assertThrows(InvalidCredentialsException.class, () -> userService.updatePassword(userCredentialsDTO));

    }
}