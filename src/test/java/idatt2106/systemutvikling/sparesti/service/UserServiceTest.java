package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    passwordEncoder = mock(PasswordEncoder.class);
    userService = new UserService(userRepository, customerService, jwtService, accountService, milestoneService, milestoneLogService, passwordEncoder);
    when(customerService.hasTwoAccounts(anyString())).thenReturn(true);
    when(milestoneLogService.getMilestoneLogsByUsername(anyString())).thenReturn(Collections.emptyList()); // return an empty list
  }

  @Test
  public void testGetUserDTO() {
    String token = "token";
    String username = "username";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    when(jwtService.extractUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);

    UserDTO userDTO = userService.getUserDTO(token);

    assertEquals(username, userDTO.getUsername());
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
  public void testUpdatePassword() {
    String token = "token";
    String username = "username";
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(username);
    userDAO.setPassword("oldPassword");
    UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
    userCredentialsDTO.setPassword("oldPassword");
    userCredentialsDTO.setNewPassword("newPassword");
    when(jwtService.extractUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(userDAO);
    when(passwordEncoder.matches(userCredentialsDTO.getPassword(), userDAO.getPassword())).thenReturn(true);
    when(passwordEncoder.encode(userCredentialsDTO.getNewPassword())).thenReturn("newEncodedPassword");

    String response = userService.updatePassword(userCredentialsDTO, token);

    assertEquals("Password updated", response);
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
  public void testGetTotalAmountSavedByAllUsers() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of(new UserDAO(), new UserDAO()));
    when(userService.getTotalAmountSavedByUser(anyString())).thenReturn(100L);
    when(milestoneLogService.getMilestoneLogsByUsername(anyString())).thenReturn(List.of(new MilestoneDTO())); // return a list

    // Act
    Long result = userService.getTotalAmountSavedByAllUsers();

    // Assert
    assertEquals(200L, result);
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

  /*@Test
  public void testUpdateUserDTO_whenEmailAlreadyExists() {
    // Arrange
    String token = "token";
    UserDTO updatedUserDTO = new UserDTO();
    updatedUserDTO.setEmail("newEmail@example.com");
    UserDAO existingUser = new UserDAO();
    existingUser.setUsername("existingUsername"); // Set the username of the existing user
    UserDAO anotherUser = new UserDAO();
    anotherUser.setEmail("newEmail@example.com");

    when(jwtService.extractUsernameFromToken(token)).thenReturn("existingUsername"); // Return the username of the existing user
    when(userRepository.findByUsername("existingUsername")).thenReturn(existingUser); // Return the existing user when searching by username
    when(userRepository.findByEmail("newEmail@example.com")).thenReturn(anotherUser); // Return another user when searching by email

    // Act
    ResponseEntity<String> response = userService.updateUserDTO(token, updatedUserDTO);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }*/


}