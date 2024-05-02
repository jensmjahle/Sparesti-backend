package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserDTO;

import java.util.logging.Logger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(
      summary = "Get total savings for all users",
      description = "Get the total amount saved by all users"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Total savings found",
              content = {
                  @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Long.class))
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content
          )
      }
  )
  @GetMapping("/get/totalSavings")
  public ResponseEntity<Long> getTotalSavingsForAllUsers() {
    logger.info("Received request to get total savings for all users.");
    Long savings = userService.getTotalAmountSavedByAllUsers();

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  @Operation(
      summary = "Get user total savings",
      description = "Get the total amount saved by the current user"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User savings found",
              content = {
                  @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Long.class))
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content
          )
      }
  )
  @GetMapping("/get/savings")
  public ResponseEntity<Long> getUserTotalSavings() {
    logger.info("Received request to get user total savings.");
    Long savings = userService.getTotalAmountSavedByUser(CurrentUserService.getCurrentUsername());

    if (savings == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    return ResponseEntity.ok(savings);
  }

  @Operation(
      summary = "Get user information",
      description = "Get the information of the current user"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User information found",
              content = {
                  @Content(mediaType = "application/json",
                      schema = @Schema(implementation = UserDTO.class))
              }
          )
      }
  )
  @GetMapping("/get")
  public ResponseEntity<UserDTO> getUserDTO() {
    logger.info("Received request to get user information.");
    return ResponseEntity.ok(userService.getUserDTO(CurrentUserService.getCurrentUsername()));
  }

  @Operation(
      summary = "Delete user information",
      description = "Delete the information of the current user"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User deleted",
              content = @Content
          ),
          @ApiResponse(
              responseCode = "404",
              description = "No user found",
              content = @Content
          )
      }
  )
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