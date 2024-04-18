package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idatt2106.systemutvikling.sparesti.service.UserService;


/**
 * Serves endpoints for retrieving, changing, and deleting user details.
 */
@RestController
@RequestMapping("/user/{username}")
@AllArgsConstructor
public class UserController {
  private final Logger logger = Logger.getLogger(UserController.class.getName());

  private final UserService userService;

  @GetMapping
  public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
    logger.info("Received request to get user information.");
    return userService.getUserDTO(username);
  }


  @DeleteMapping
  public ResponseEntity<UserDTO> deleteUser(@PathVariable String username) {
    logger.info("Received request to delete user information.");
    return userService.deleteUserDTO(username);
  }

  /**
   * Updates user details with the new values specified by the request body.
   * @param username The user ID identifying a specific user.
   * @param updatedUserDTO The new information to replace the old information. Any non-null fields will replace the
   *                       old information. All null fields will be ignored.
   */
  @PutMapping
  public ResponseEntity<String> updateUserDetails(@PathVariable String username, @RequestBody UserDTO updatedUserDTO) {
    logger.info("Received request to update user information.");
    return userService.updateUserDTO(username, updatedUserDTO);
  }
}
