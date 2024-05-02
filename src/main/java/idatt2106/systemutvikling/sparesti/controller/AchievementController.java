package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.service.AchievementService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for handling achievements.
 */
@RestController
@RequestMapping("/achievement")
@AllArgsConstructor
public class AchievementController {

  private final Logger logger = Logger.getLogger(AchievementController.class.getName());
  private final AchievementService achievementService;

  @Operation(
          summary = "Get all locked achievements",
          description = "Get all locked achievements for the current user."
  )
  @ApiResponse(
          responseCode = "200",
          description = "Locked achievements returned",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = AchievementDTO.class))
          }
  )
  @GetMapping("/locked")
  public ResponseEntity<List<AchievementDTO>> getLockedAchievements() {
    logger.info("Received request to get locked achievements.");
    return ResponseEntity.ok(
            achievementService.getLockedAchievementsAsDTOS(CurrentUserService.getCurrentUsername()));
  }

  @Operation(
          summary = "Get all unlocked achievements",
          description = "Get all unlocked achievements for the current user."
  )
  @ApiResponse(
          responseCode = "200",
          description = "Unlocked achievements returned",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = AchievementDTO.class))
          }
  )
  @GetMapping("/newUnlocked")
  public ResponseEntity<List<AchievementDTO>> getNewUnlockedAchievements() {
    return ResponseEntity.ok(
            achievementService.checkForUnlockedAchievements(CurrentUserService.getCurrentUsername()));
  }
}
