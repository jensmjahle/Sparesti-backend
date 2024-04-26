package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.service.AchievementService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.utils.ResponseEntityExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/achievement")
@AllArgsConstructor
public class AchievementController {

  private final Logger logger = Logger.getLogger(AchievementController.class.getName());
  private final AchievementService achievementService;

  @GetMapping("/locked")
  public ResponseEntity<List<AchievementDTO>> getLockedAchievements() {
    try {
      return ResponseEntity.ok(
          achievementService.getLockedAchievementsAsDTOS(CurrentUserService.getCurrentUsername()));
    } catch (Exception e) {
      logger.severe("Failed to get locked achievements.");
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/newUnlocked")
  public ResponseEntity<List<AchievementDTO>> getNewUnlockedAchievements() {
    try {
      return ResponseEntity.ok(
          achievementService.checkForUnlockedAchievements(CurrentUserService.getCurrentUsername()));
    } catch (Exception e) {
      logger.severe("Failed to get new unlocked achievements.");
      return ResponseEntityExceptionHandler.handleException(e);
    }
  }
}
