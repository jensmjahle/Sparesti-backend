package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.mapper.AchievementMapper;
import idatt2106.systemutvikling.sparesti.repository.AchievementRepository;
import idatt2106.systemutvikling.sparesti.repository.ConditionRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing achievements.
 */
@Service
public class AchievementService {

  private final Logger logger = Logger.getLogger(AchievementService.class.getName());

  private final AchievementRepository achievementRepository;
  private final ConditionRepository conditionRepository;
  private final ConditionService conditionService;
  private final UserRepository userRepository;

  /**
   * Constructor for AchievementService.
   *
   * @param achievementRepository The repository for achievements.
   * @param conditionRepository   The repository for conditions.
   * @param conditionService      The service for condition management.
   * @param userRepository        The repository for users.
   */
  public AchievementService(AchievementRepository achievementRepository,
      ConditionRepository conditionRepository,
      ConditionService conditionService,
      UserRepository userRepository) {
    this.achievementRepository = achievementRepository;
    this.conditionRepository = conditionRepository;
    this.conditionService = conditionService;
    this.userRepository = userRepository;
  }

  /**
   * Retrieves the list of locked achievements for a given user.
   *
   * @param username The user for whom to retrieve locked achievements.
   * @return The list of locked achievements.
   */
  public List<AchievementDAO> getLockedAchievements(String username) {
    UserDAO user = userRepository.findByUsername(username);
    if (user == null) {
      // Handle the case when the user is not found
      return new ArrayList<>();
    }

    List<AchievementDAO> allAchievements = new ArrayList<>(achievementRepository.findAll());
    List<AchievementDAO> userAchievements = user.getAchievements();
    allAchievements.removeAll(userAchievements);
    return allAchievements;
  }

  /**
   * Retrieves locked achievements for a user and converts them to AchievementDTOs.
   *
   * @param username The username of the user for whom locked achievements are to be retrieved.
   * @return ResponseEntity containing a list of AchievementDTOs representing the locked
   * achievements.
   */
  public List<AchievementDTO> getLockedAchievementsAsDTOS(String username) {
    List<AchievementDAO> achievedDAOS = getLockedAchievements(username);
    return AchievementMapper.toDTOList(achievedDAOS);
  }

  /**
   * Checks for unlocked achievements for a given user and adds them to the user's achievements if
   * unlocked.
   *
   * @param username The user for whom to check and unlock achievements.
   * @return The list of newly unlocked achievements.
   */
  public List<AchievementDTO> checkForUnlockedAchievements(String username) {
    UserDAO user = userRepository.findByUsername(username);
    if (user == null) {
      // Handle the case when the user is not found
      return new ArrayList<>();
    }
    List<AchievementDTO> newAchievements = new ArrayList<>();
    List<AchievementDAO> lockedAchievement = getLockedAchievements(user.getUsername());
    List<ConditionDAO> conditions;
    boolean achievementUnlocked;
    for (AchievementDAO achievement : lockedAchievement) {
      conditions = conditionRepository.findAllByAchievementDAO_AchievementId(
              achievement.getAchievementId());
      if (!conditions.isEmpty()) achievementUnlocked = true;
      else break;
      for (ConditionDAO condition : conditions) {
        if (!conditionService.isConditionMet(condition)) {
          achievementUnlocked = false;
          break;
        }
      }
      if (achievementUnlocked) {
        user.getAchievements().add(achievement);
        userRepository.save(user);
        newAchievements.add(AchievementMapper.toDTO(achievement));
      }
    }
    return newAchievements;
  }
}
