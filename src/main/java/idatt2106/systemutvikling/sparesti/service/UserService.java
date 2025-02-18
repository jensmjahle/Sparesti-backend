package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.exceptions.BankConnectionErrorException;
import idatt2106.systemutvikling.sparesti.exceptions.ConflictException;
import idatt2106.systemutvikling.sparesti.exceptions.InvalidCredentialsException;
import idatt2106.systemutvikling.sparesti.exceptions.UserNotFoundException;
import idatt2106.systemutvikling.sparesti.mapper.Base64Mapper;
import idatt2106.systemutvikling.sparesti.mapper.UserMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.repository.ManualSavingRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for the services related to the User entity.
 */
@Service
@AllArgsConstructor
public class UserService {

  private PasswordEncoder passwordEncoder;
  private final CustomerServiceInterface customerService;
  private final AccountServiceInterface accountService;
  private final JWTService jwtService;

  private final MilestoneService milestoneService;
  private final MilestoneLogService milestoneLogService;

  private final UserRepository userRepository;
  private final ManualSavingRepository dbSaving;
  private final MilestoneRepository dbMilestone;
  private final MilestoneLogRepository dbMilestoneLog;
  private final ChallengeLogRepository dbChallenge;
  private final ChallengeRepository dbChallengeLog;

  private final Logger logger = Logger.getLogger(UserService.class.getName());

  /**
   * Method to get a user by username from the database.
   *
   * @param username The username of the user to get.
   * @return ResponseEntity with the User object and status code.
   */
  public UserDTO getUserDTO(String username) {
    try {
      UserDAO userDAO = userRepository.findByUsername(username);
      UserDTO userDTO = UserMapper.toUserDTO(userDAO);

      try {

        if (customerService.hasTwoAccounts(username)
            && accountService.findAccountsByUsername(username).size() >= 2 &&
            accountService.findAccountsNumbersByUsername(username)
                .contains(userDAO.getCurrentAccount()) &&
            accountService.findAccountsNumbersByUsername(username)
                .contains(userDAO.getSavingsAccount())) {
          userDTO.setIsConnectedToBank(true);
        } else {
          userDTO.setIsConnectedToBank(false);
        }

      } catch (Exception e) {
        logger.severe("Error when checking if user has two accounts: " + e.getMessage());
        throw new BankConnectionErrorException("Error when checking if user has two accounts");
      }

      return userDTO;
    } catch (Exception e) {
      logger.severe("Error when getting user: " + e.getMessage());
      throw new UserNotFoundException("User not found");
    }
  }

  /**
   * Calculates the total amount saved by all users in the system by summing up the savings of each
   * individual user.
   *
   * @return The total amount saved by all users, or {@code null} if an error occurs during
   * calculation.
   */
  public Long getTotalAmountSavedByAllUsers() {
    Long result = 0L;
    try {
      List<UserDAO> users = userRepository.findAll();
      String username;
      for (UserDAO user : users) {
        username = user.getUsername();
        result += getTotalAmountSavedByUser(username);
      }
      return result;
    } catch (Exception e) {
      logger.severe("Error when getting milestones and calculating savings: " + e.getMessage());
      return null; // Return null to indicate error
    }
  }

  /**
   * Retrieves the total amount saved by the user based on active milestones and milestone logs.
   *
   * @param username The authentication token of the user.
   * @return The total amount saved by the user.
   */
  public Long getTotalAmountSavedByUser(String username) {
    Long result = 0L;

    try {
      List<MilestoneDTO> milestones = milestoneService.getActiveMilestonesDTOsByUsername(username);

      for (MilestoneDTO milestone : milestones) {
        result += milestone.getMilestoneCurrentSum();
      }

      milestones = milestoneLogService.getMilestoneLogsByUsername(username);

      for (MilestoneDTO milestone : milestones) {
        result += milestone.getMilestoneCurrentSum();
      }

      return result;

    } catch (Exception e) {
      logger.severe("Error when getting milestones and calculating savings: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to update a user in the database.
   *
   * @param token          The token of the user to update.
   * @param updatedUserDTO The updated UserDTO object with all fields.
   * @return ResponseEntity with the status code.
   */
  public ResponseEntity<String> updateUserDTO(String token, UserDTO updatedUserDTO) {
    String username = jwtService.extractUsernameFromToken(token);

    UserDAO existingUser = userRepository.findByUsername(username);
    if (existingUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (Objects.nonNull(updatedUserDTO.getProfilePictureBase64())) {
      existingUser.setProfilePicture(
          Base64Mapper.toByteArray(updatedUserDTO.getProfilePictureBase64()));
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
      UserDAO userWithNewEmail = userRepository.findByEmail(updatedUserDTO.getEmail());
      if (userWithNewEmail != null && !userWithNewEmail.getUsername().equals(username)) {
        return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
      }
      existingUser.setEmail(updatedUserDTO.getEmail());
    }

    userRepository.save(existingUser);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Method to create a user from a UserCredentialsDTO object.
   *
   * @param user The UserCredentialsDTO object to create the user from.
   * @return ResponseEntity with the UserDTO object and status code.
   */
  public UserDTO createUser(UserCredentialsDTO user) {
    UserDAO userDAO = UserMapper.userCredentialsDTOToUserDAO(user);

    if (userRepository.findByUsername(userDAO.getUsername()) != null) {
      throw new ConflictException("Username already in use");
    }
    if (passwordEncoder.encode(userDAO.getPassword()).length() <= 8 || userDAO.getPassword() == null
        || userDAO.getUsername() == null) {
      throw new InvalidCredentialsException("Password needs to be at least 8 characters long");
    }

    if (userRepository.findByUsername(userDAO.getUsername()) != null
        || userRepository.findByEmail(userDAO.getEmail()) != null) {
      throw new ConflictException("Username or email already in use");
    }

    userDAO.setPassword(passwordEncoder.encode(userDAO.getPassword()));
    userRepository.save(userDAO);
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername(userDAO.getUsername());
    userDTO.setIsConnectedToBank(false);

    return userDTO;
  }


  /**
   * Method to update the password of a user.
   *
   * @param userCredentialsDTO The UserCredentialsDTO object with the new password.
   * @return ResponseEntity with the status code.
   */
  public String updatePassword(UserCredentialsDTO userCredentialsDTO) {
    UserDAO userDAO = userRepository.findByUsername(CurrentUserService.getCurrentUsername());
    if (!passwordEncoder.matches(userCredentialsDTO.getPassword(), userDAO.getPassword())
        || userCredentialsDTO.getNewPassword() == null) {
      throw new InvalidCredentialsException("Invalid password");
    }
    if (userCredentialsDTO.getNewPassword().length() <= 8) {
      throw new InvalidCredentialsException("Password needs to be at least 8 characters long");
    }

    userDAO.setPassword(passwordEncoder.encode(userCredentialsDTO.getNewPassword()));
    userRepository.save(userDAO);
    return "Password updated";
  }


  /**
   * Method to delete the current user using CurrentUserService.
   *
   * @return {@code true} if the user was deleted, {@code false} otherwise.
   */
  public boolean deleteCurrentUser() {
    String username = CurrentUserService.getCurrentUsername();

    if (username == null) {
      return false;
    }

    return deleteUserByUsername(username);
  }

  public boolean deleteUserByUsername(@NonNull String username) {

    // Check whether user exists
    if (userRepository.findByUsername(username) == null) {
      return false;
    }

    // Delete manual savings
    dbSaving.deleteAllByUser_Username(username);

    // Delete milestones
    dbMilestone.deleteAllByUserDAO_Username(username);

    // Delete milestone log
    dbMilestoneLog.deleteAllByUserDAO_Username(username);

    // Delete challenges
    dbChallenge.deleteAllByUserDAO_Username(username);

    // Delete challenge log
    dbChallengeLog.deleteAllByUserDAO_Username(username);

    // Finally, delete user
    userRepository.deleteByUsername(username);

    return true;
  }
}
