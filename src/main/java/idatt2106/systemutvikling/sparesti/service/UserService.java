package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.model.LoginRequestModel;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import idatt2106.systemutvikling.sparesti.mapper.UserMapper;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final CustomerServiceInterface customerService;
  private final JWTService jwtService;
  private final Logger logger = Logger.getLogger(UserService.class.getName());

  @Autowired
  public UserService(UserRepository userRepository, CustomerServiceInterface customerService, JWTService jwtService) {
    this.userRepository = userRepository;
    this.customerService = customerService;
    this.jwtService = jwtService;
  }

  /**
   * Method to get a user by username from the database.
   * @param token The token of the user to get.
   * @return ResponseEntity with the User object and status code.
   */
  public ResponseEntity<UserDTO> getUserDTO(String token) {

    String username = jwtService.extractUsernameFromToken(token);

    try {
    UserDAO userDAO = userRepository.findByUsername(username);
    UserDTO userDTO = UserMapper.toUserDTO(userDAO);

    try {

      if (customerService.hasTwoAccounts(username)) {
        userDTO.setIsConnectedToBank(true);
      } else {
        userDTO.setIsConnectedToBank(false);
      }
    } catch (Exception e) {
      logger.severe("Error when checking if user has two accounts: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(userDTO, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Method to update a user in the database.
   * @param token The token of the user to update.
   * @param updatedUserDTO The updated UserDTO object with all fields.
   * @return ResponseEntity with the status code.
   */
  public ResponseEntity<String> updateUserDTO(String token, UserDTO updatedUserDTO) {
    //TODO: Update userDTO in database

    // Find the user in the database based on the username, and then update the fields given in the updatedUserDTO
    // if they are not null and not equal to the existing values.

    String username = jwtService.extractUsernameFromToken(token);

    UserDAO existingUser = userRepository.findByUsername(username);
    if (existingUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (Objects.nonNull(updatedUserDTO.getProfilePictureBase64())) {
      existingUser.setProfilePicture(updatedUserDTO.getProfilePictureBase64().getBytes());
    }

    if (Objects.nonNull(updatedUserDTO.getCurrentAccount())) {
      existingUser.setCurrentAccount(updatedUserDTO.getCurrentAccount());
    }

    if (Objects.nonNull(updatedUserDTO.getSavingsAccount())) {
      existingUser.setSavingsAccount(updatedUserDTO.getSavingsAccount());
    }

    if (Objects.nonNull(updatedUserDTO.getMonthlyIncome())) {
      existingUser.setMonthlyIncome(updatedUserDTO.getMonthlyIncome());
    }

    if (Objects.nonNull(updatedUserDTO.getMonthlySavings())) {
      existingUser.setMonthlySavings(updatedUserDTO.getMonthlySavings());
    }

    if (Objects.nonNull(updatedUserDTO.getMonthlyFixedExpenses())) {
      existingUser.setMonthlyFixedExpenses(updatedUserDTO.getMonthlyFixedExpenses());
    }

    if (Objects.nonNull(updatedUserDTO.getFirstName())) {
      existingUser.setFirstName(updatedUserDTO.getFirstName());
    }

    if (Objects.nonNull(updatedUserDTO.getLastName())) {
      existingUser.setLastName(updatedUserDTO.getLastName());
    }

    if (Objects.nonNull(updatedUserDTO.getBirthDate())) {
      existingUser.setBirthDate(updatedUserDTO.getBirthDate());
    }

    if (Objects.nonNull(updatedUserDTO.getEmail())) {
      existingUser.setEmail(updatedUserDTO.getEmail());
    }

    userRepository.save(existingUser);

    return null;
  }

  public ResponseEntity<UserDTO> deleteUserDTO(String token) {
    //TODO: Delete userDTO from database
    String username = jwtService.extractUsernameFromToken(token);

    return null;
  }

  public ResponseEntity<UserDTO> login(LoginRequestModel user) {
    //TODO: Check if user exists in database, and if password is correct
    //TODO: Return userDTO if login is successful
    //TODO: Return only profile picture, username and isConnectedToBank if login is successful
    return null;
  }

  /**
   * Method to create a user from a UserCredentialsDTO object.
   * @param user The UserCredentialsDTO object to create the user from.
   * @return ResponseEntity with the UserDTO object and status code.
   */
  public ResponseEntity<String> createUser(UserCredentialsDTO user) {
    try {
      UserDAO userDAO = UserMapper.userCredentialsDTOToUserDAO(user);

      if (userRepository.findByUsername(userDAO.getUsername()) != null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      } else {
        try {
          userDAO.setPassword(passwordEncoder.encode(userDAO.getPassword()));
          userRepository.save(userDAO);
          //here we should return only profile picture, username and isConnectedToBank
          UserDTO userDTO = new UserDTO();
          userDTO.setUsername(userDAO.getUsername());
          userDTO.setIsConnectedToBank(UserMapper.toUserDTO(userDAO).getIsConnectedToBank());
          return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Method to update the password of a user.
   *
   * @param user The UserCredentialsDTO object with the new password.
   * @return ResponseEntity with the status code.
   */
  public ResponseEntity<String> updatePassword(UserCredentialsDTO user, String token) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      UserDAO userDAO = userRepository.findByUsername(username);
      if (!passwordEncoder.matches(user.getPassword(), userDAO.getPassword())) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      userDAO.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(userDAO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
