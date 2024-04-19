package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idatt2106.systemutvikling.sparesti.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  private final Logger logger = Logger.getLogger(UserController.class.getName());
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/get/{username}")
  public ResponseEntity<UserDTO> getUserDTO(@PathVariable String username) {
    logger.info("Received request to get user information.");
    return userService.getUserDTO(username);
  }

  @RequestMapping("/delete/{username}")
  public ResponseEntity<UserDTO> deleteUserDTO(@PathVariable String username) {
    logger.info("Received request to delete user information.");
    return userService.deleteUserDTO(username);
  }

@RequestMapping("/update/{username}")
  public ResponseEntity<String> updateUserDTO(@PathVariable String username, @RequestBody UserDTO updatedUserDTO) {
    logger.info("Received request to update user information.");
    return userService.updateUserDTO(username, updatedUserDTO);
  }

}
