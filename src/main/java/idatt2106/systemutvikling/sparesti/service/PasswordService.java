package idatt2106.systemutvikling.sparesti.service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;

/**
 * Service class for security operations.
 */
@Service
public class PasswordService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final Logger logger = Logger.getLogger(PasswordService.class.getName());

  @Autowired
  public PasswordService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Method to check if the password is correct for a given username with salt and hash.
   * @param username the username of the user
   * @param password the password to check from frontend unhashed
   * @return true if the password is correct, false otherwise
   */
  public boolean correctPassword(String username, String password) {
    try {
      UserDAO user = userRepository.findByUsername(username);
      logger.info(passwordEncoder.encode(password));
      return passwordEncoder.matches(password, user.getPassword());
    } catch (Exception e) {
      logger.severe("No user with username " + username + " found: " + e.getMessage());
      throw new IllegalArgumentException("No user with username " + username + " found: ");
    }

  }
}

