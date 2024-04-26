package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.mapper.UserMapper;
import idatt2106.systemutvikling.sparesti.model.LoginRequestModel;
import idatt2106.systemutvikling.sparesti.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  private final CustomerServiceInterface customerService;
  private final AccountServiceInterface accountService;
  private final JWTService jwtService;

  private final UserRepository userRepository;
  private final ManualSavingRepository dbSaving;
  private final MilestoneRepository dbMilestone;
  private final MilestoneLogRepository dbMilestoneLog;
  private final ChallengeLogRepository dbChallenge;
  private final ChallengeRepository dbChallengeLog;

  private final Logger logger = Logger.getLogger(UserService.class.getName());

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

      if (customerService.hasTwoAccounts(username) && accountService.findAccountsByUsername(username).size() >= 2 &&
              accountService.findAccountsNumbersByUsername(username).contains(userDAO.getCurrentAccount()) &&
              accountService.findAccountsNumbersByUsername(username).contains(userDAO.getSavingsAccount())) {
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

    if (Objects.nonNull(updatedUserDTO.getEmail()) && userRepository.findByEmail(existingUser.getEmail()) == null) {
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
  public ResponseEntity<UserDTO> createUser(UserCredentialsDTO user) {
    try {
      UserDAO userDAO = UserMapper.userCredentialsDTOToUserDAO(user);

      if (userRepository.findByUsername(userDAO.getUsername()) != null) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      } else {

        if (passwordEncoder.encode(userDAO.getPassword()).length() <= 8 || userDAO.getPassword() == null || userDAO.getUsername() == null){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByUsername(userDAO.getUsername()) != null || userRepository.findByEmail(userDAO.getEmail()) != null) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
          userDAO.setPassword(passwordEncoder.encode(userDAO.getPassword()));
          userRepository.save(userDAO);
          UserDTO userDTO = new UserDTO();
          userDTO.setUsername(userDAO.getUsername());
          userDTO.setIsConnectedToBank(false);

          return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
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
      if (!passwordEncoder.matches(user.getPassword(), userDAO.getPassword()) || user.getNewPassword() == null) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      if (passwordEncoder.encode(user.getNewPassword()).length() <= 8) {
        return new ResponseEntity<>("Password needs to be at least 8 characters long",HttpStatus.BAD_REQUEST);
      }

      userDAO.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(userDAO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  public boolean deleteCurrentUser() {
    String username = CurrentUserService.getCurrentUsername();

    return deleteUserByUsername(username);
  }

  @Transactional
  public boolean deleteUserByUsername(@NonNull String username) {

    // Check whether user exists
    if (userRepository.findByUsername(username) == null)
      return false;

    // Delete manual savings
    dbSaving.deleteAllByUser_Username(username);

    // Delete milestones
    dbMilestone.deleteAllByUserDAO_Username(username);

    // Delete milestone log
    dbMilestoneLog.deleteAllByUserDAO_Username(username);

    // Delete challenges
    dbMilestone.deleteAllByUserDAO_Username(username);

    // Delete challenge log
    dbMilestone.deleteAllByUserDAO_Username(username);

    // Finally, delete user
    userRepository.deleteByUsername(username);

    return true;
  }
}
