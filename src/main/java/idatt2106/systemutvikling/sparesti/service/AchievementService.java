package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.mapper.AchievementMapper;
import idatt2106.systemutvikling.sparesti.repository.AchievementRepository;
import idatt2106.systemutvikling.sparesti.repository.ConditionRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing achievements.
 */
@Service
public class AchievementService {

  private final AchievementRepository achievementRepository;
  private final ConditionRepository conditionRepository;
  private final ConditionService conditionService;
  private final UserRepository userRepository;

  /**
   * Constructor for AchievementService.
   *
   * @param achievementRepository The repository for achievements.
   * @param conditionRepository The repository for conditions.
   * @param conditionService The service for condition management.
   * @param userRepository The repository for users.
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
   * @param user The user for whom to retrieve locked achievements.
   * @return The list of locked achievements.
   */
  public List<AchievementDAO> getLockedAchievements(UserDAO user) {
    List<AchievementDAO> achieved = user.getAchievements();
    List<AchievementDAO> lockedAchievements = new ArrayList<>(achievementRepository.findAll());
    lockedAchievements.removeAll(achieved);
    return lockedAchievements;
  }

  /**
   * Checks for unlocked achievements for a given user and adds them to the user's achievements if unlocked.
   *
   * @param user The user for whom to check and unlock achievements.
   * @return The list of newly unlocked achievements.
   */
  public List<AchievementDTO> checkForUnlockedAchievements(UserDAO user) {
    List<AchievementDTO> newAchievements = new ArrayList<>();
    List<AchievementDAO> lockedAchievement = getLockedAchievements(user);
    List<ConditionDAO> conditions;
    boolean achievementUnlocked;
    for (AchievementDAO achievement : lockedAchievement) {
      achievementUnlocked = true;
      conditions = conditionRepository.findAllByAchievementDAO_AchievementId(achievement.getAchievementId());
      for (ConditionDAO condition : conditions) {
        if(!conditionService.isConditionMet(condition)) {
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
