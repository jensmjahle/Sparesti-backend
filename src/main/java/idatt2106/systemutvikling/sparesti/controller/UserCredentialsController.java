package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.service.UserService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user credentials.
 */
@CrossOrigin
@RestController
@RequestMapping("/userCredentials")
public class UserCredentialsController {

  private final UserService userService;
  Logger logger = Logger.getLogger(UserCredentialsController.class.getName());

  @Autowired
  public UserCredentialsController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Method for creating a user.
   *
   * @param user the user to create
   * @return the created user
   */
  @RequestMapping("/create")
  public ResponseEntity<UserDTO> createUser(@RequestBody UserCredentialsDTO user) {
    logger.info("Received request to create user with username: " + user.getUsername() + ".");
    return ResponseEntity.ok(userService.createUser(user));
  }

  /**
   * Method for updating a user.
   *
   * @param userCredentialsDTO the user to update
   * @return the updated user
   */
  @PutMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(@RequestBody UserCredentialsDTO userCredentialsDTO) {
    logger.info(
        "Received request to update password for user with username: " + userCredentialsDTO.getUsername() + ".");
    return ResponseEntity.ok(userService.updatePassword(userCredentialsDTO));
  }

}
