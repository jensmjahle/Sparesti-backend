package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;

import java.util.logging.Logger;

import jakarta.transaction.Transactional;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idatt2106.systemutvikling.sparesti.service.UserService;

/**
 * Controller for handling user information.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

  private final Logger logger = Logger.getLogger(UserController.class.getName());
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Method for getting total savings for all users.
   *
   * @return total savings for all users
   */
  @GetMapping("/get/totalSavings")
  public ResponseEntity<Long> getTotalSavingsForAllUsers() {
    logger.info("Received request to get total savings for all users.");
    Long savings = userService.getTotalAmountSavedByAllUsers();

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  /**
   * Method for getting total savings for a user.
   *
   * @return total savings for a user
   */
  @GetMapping("/get/savings")
  public ResponseEntity<Long> getUserTotalSavings() {
    logger.info("Received request to get user total savings.");
    Long savings = userService.getTotalAmountSavedByUser(CurrentUserService.getCurrentUsername());

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  /**
   * Method for getting user information.
   *
   * @return user information
   */
  @GetMapping("/get")
  public ResponseEntity<UserDTO> getUserDTO() {
    logger.info("Received request to get user information.");
    return ResponseEntity.ok(userService.getUserDTO(CurrentUserService.getCurrentUsername()));
  }

  /**
   * Method for deleting user information.
   *
   * @return response entity
   */
  @Transactional
  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteUserDTO() {
    logger.info("Received request to delete user information.");

    boolean deleted = userService.deleteCurrentUser();

    return deleted ?
            ResponseEntity.ok().body("User deleted successfully") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
  }

  /**
   * Method for updating user information.
   *
   * @param token the token
   * @param updatedUserDTO the updated user information
   * @return response entity
   */
  @PutMapping("/update")
  public ResponseEntity<String> updateUserDTO(@RequestHeader("Authorization") String token,
      @RequestBody UserDTO updatedUserDTO) {
    logger.info("Received request to update user information.");
    userService.updateUserDTO(token, updatedUserDTO);
    return ResponseEntity.ok("User information updated.");
  }

}