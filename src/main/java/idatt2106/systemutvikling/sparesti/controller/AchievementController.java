package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.service.AchievementService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
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

  /**
   * Method for getting all achievements that have yet to be completed by the user.
   *
   * @return all locked achievements
   */
  @GetMapping("/locked")
  public ResponseEntity<List<AchievementDTO>> getLockedAchievements() {
    logger.info("Received request to get locked achievements.");
    return ResponseEntity.ok(
        achievementService.getLockedAchievementsAsDTOS(CurrentUserService.getCurrentUsername()));
  }

  /**
   * Method for getting all achievements that have been completed by the user.
   *
   * @return all unlocked achievements
   */
  @GetMapping("/newUnlocked")
  public ResponseEntity<List<AchievementDTO>> getNewUnlockedAchievements() {
    return ResponseEntity.ok(
        achievementService.checkForUnlockedAchievements(CurrentUserService.getCurrentUsername()));
  }
}
