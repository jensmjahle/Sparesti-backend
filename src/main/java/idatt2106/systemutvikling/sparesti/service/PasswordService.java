package idatt2106.systemutvikling.sparesti.service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final Logger logger = Logger.getLogger(PasswordService.class.getName());

  @Autowired
  public SecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public boolean correctPassword(String username, String password) {
    try {
      UserDAO user = userRepository.findByUsername(username);
      logger.info(passwordEncoder.encode(password));
      return passwordEncoder.matches(password, user.getPassword());
      //return user.getPassword().equals(password);
    } catch (Exception e) {
      logger.severe("No user with username " + username + " found: " + e.getMessage());
      throw new IllegalArgumentException("No user with username " + username + " found: ");
    }

  }
}

