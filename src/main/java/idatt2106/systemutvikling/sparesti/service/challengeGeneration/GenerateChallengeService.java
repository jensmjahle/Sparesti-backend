package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeData;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.service.ChallengeLogService;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenerateChallengeService {

  private final UserRepository userRepository;
  private final Logger logger = Logger.getLogger(GenerateChallengeService.class.getName());
  private final ChallengeGeneratorImpl challengeGenerator;
  private final ChallengeService challengeService;
  private final ChallengeLogService challengeLogService;


  /**
   * Generates one daily challenge for all users in the database. This method is called once a day
   * by GenerateChallengeTask.
   */
  public void generateDailyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
        } catch (Exception e) {
          logger.severe("Failed to generate daily challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate daily challenges" + e.getMessage());
    }
  }

  /**
   * Generates one weekly challenge for all users in the database. This method is called once a week
   * by GenerateChallengeTask.
   */
  public void generateWeeklyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 7);
        } catch (Exception e) {
          logger.severe("Failed to generate weekly challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate weekly challenges" + e.getMessage());
    }
  }

  /**
   * Generates one monthly challenge for all users in the database. This method is called once a
   * month by GenerateChallengeTask.
   */
  public void generateMonthlyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      int daysInMonth = LocalDateTime.now().toLocalDate().lengthOfMonth();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), daysInMonth);
        } catch (Exception e) {
          logger.severe("Failed to generate monthly challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate monthly challenges" + e.getMessage());
    }
  }

  /**
   * Generates random challenges for all users in the database.
   */
  public void generateRandomChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
        } catch (Exception e) {
          logger.severe("Failed to generate random challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate random challenges" + e.getMessage());
    }
  }

  /**
   * Generates a challenge for a user.
   *
   * @param user The user to generate the challenge for.
   */
  public void generateInstantChallenge(UserDAO user) {
    try {
      challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
    } catch (Exception e) {
      logger.severe("Failed to generate instant challenge for user " + user.getUsername() + "."
          + e.getMessage());
    }
  }

  public ChallengeData initializeChallengeData(UserDAO user) {
    List<ChallengeDAO> activeChallenges = challengeService.getChallengesByActiveAndUsername(
        user.getUsername(), true);
    List<ChallengeDAO> inactiveChallenges = challengeService.getChallengesByActiveAndUsername(
        user.getUsername(), false);
    int activeChallengesCount = activeChallenges.size();
    int inactiveChallengesCount = inactiveChallenges.size();
    double challengeCompletionRate = challengeLogService.getChallengeCompletionRate(
        user.getUsername());

    return null;
  }
}
