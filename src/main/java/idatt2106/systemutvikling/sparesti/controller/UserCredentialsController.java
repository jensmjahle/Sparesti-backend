package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.service.UserService;

import java.util.logging.Logger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(
          summary = "Create user",
          description = "Create a new user"
  )
  @ApiResponse(
          responseCode = "200",
          description = "User created",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = UserDTO.class))
          }
  )
  @Parameter(
          name = "user",
          description = "The user to create",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = UserCredentialsDTO.class)
                  )
          }
  )
  @RequestMapping("/create")
  public ResponseEntity<UserDTO> createUser(@RequestBody UserCredentialsDTO user) {
    logger.info("Received request to create user with username: " + user.getUsername() + ".");
    return ResponseEntity.ok(userService.createUser(user));
  }

  @Operation(
          summary = "Update password",
          description = "Update the password for a user"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Password updated",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = String.class)
                  )
          }
  )
  @Parameter(
          name = "userCredentialsDTO",
          description = "The user credentials to update",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = UserCredentialsDTO.class)
                  )
          }
  )
  @PutMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(@RequestBody UserCredentialsDTO userCredentialsDTO) {
    logger.info(
            "Received request to update password for user with username: " + userCredentialsDTO.getUsername() + ".");
    return ResponseEntity.ok(userService.updatePassword(userCredentialsDTO));
  }

}
