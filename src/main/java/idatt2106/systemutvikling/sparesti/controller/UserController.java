package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import java.util.logging.Logger;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idatt2106.systemutvikling.sparesti.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  private final Logger logger = Logger.getLogger(UserController.class.getName());
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/get")
  public ResponseEntity<UserDTO> getUserDTO(@RequestHeader("Authorization") String token) {
    logger.info("Received request to get user information.");
    return userService.getUserDTO(token);
  }

  @Transactional
  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteUserDTO() {
    logger.info("Received request to delete user information.");

    boolean deleted = userService.deleteCurrentUser();

    return deleted ?
            ResponseEntity.ok().body("User deleted successfully") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
  }

  @RequestMapping("/update")
  public ResponseEntity<String> updateUserDTO(@RequestHeader("Authorization") String token, @RequestBody UserDTO updatedUserDTO) {
    logger.info("Received request to update user information.");
    return userService.updateUserDTO(token, updatedUserDTO);
  }

}