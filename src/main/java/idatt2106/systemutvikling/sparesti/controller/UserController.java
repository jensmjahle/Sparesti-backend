package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;

import java.util.logging.Logger;

import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import idatt2106.systemutvikling.sparesti.service.UserService;

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

  @GetMapping("/get/totalSavings")
  public ResponseEntity<Long> getTotalSavingsForAllUsers() {
    logger.info("Received request to get total savings for all users.");
    Long savings = userService.getTotalAmountSavedByAllUsers();

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  @GetMapping("/get/savings")
  public ResponseEntity<Long> getUserTotalSavings() {
    logger.info("Received request to get user total savings.");
    Long savings = userService.getTotalAmountSavedByUser(CurrentUserService.getCurrentUsername());

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  @GetMapping("/get")
  public ResponseEntity<UserDTO> getUserDTO(@RequestHeader("Authorization") String token) {
    logger.info("Received request to get user information.");
    return ResponseEntity.ok(userService.getUserDTO(token));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<UserDTO> deleteUserDTO(@RequestHeader("Authorization") String token) {
    logger.info("Received request to delete user information.");
    return ResponseEntity.ok(userService.deleteUserDTO(token));
  }

  @PutMapping("/update")
  public ResponseEntity<String> updateUserDTO(@RequestHeader("Authorization") String token,
      @RequestBody UserDTO updatedUserDTO) {
    logger.info("Received request to update user information.");
    userService.updateUserDTO(token, updatedUserDTO);
    return ResponseEntity.ok("User information updated.");
  }

}