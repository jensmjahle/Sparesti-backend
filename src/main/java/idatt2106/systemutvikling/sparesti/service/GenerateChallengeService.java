package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateChallengeService {
  private final UserRepository userRepository;
  private ChallengeGenerator challengeGenerator;
  private final Logger logger = Logger.getLogger(GenerateChallengeService.class.getName());

  @Autowired
  public GenerateChallengeService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Generates one daily challenge for all users in the database.
   * This method is called once a day by GenerateChallengeTask.
   */
  public void generateDailyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        challengeGenerator.generateDailyChallenges(user);
      }
    } catch (Exception e) {
      logger.severe("Failed to generate daily challenges" + e.getMessage());
    }
  }

  /**
   * Generates one weekly challenge for all users in the database.
   * This method is called once a week by GenerateChallengeTask.
   */
  public void generateWeeklyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        challengeGenerator.generateWeeklyChallenges(user);
      }
    } catch (Exception e) {
      logger.severe("Failed to generate weekly challenges" + e.getMessage());
    }
  }

  /**
   * Generates one monthly challenge for all users in the database.
   * This method is called once a month by GenerateChallengeTask.
   */
  public void generateMonthlyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        challengeGenerator.generateMonthlyChallenges(user);
      }
    } catch (Exception e) {
      logger.severe("Failed to generate monthly challenges" + e.getMessage());
    }
  }

  /**
   * Generates random challenges for all users in the database.
   *
   */
  public void generateRandomChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        challengeGenerator.generateRandomChallenges(user);
      }
    } catch (Exception e) {
      logger.severe("Failed to generate random challenges" + e.getMessage());
    }
  }

  /**
   * Generates an instant challenge for a user.
   * @param user The user to generate the challenge for.
   */
  public void generateInstantChallenge(UserDAO user) {
    try {
      challengeGenerator.generateInstantChallenge(user);
    } catch (Exception e) {
      logger.severe("Failed to generate instant challenge for user " + user.getUsername() + "."
          + e.getMessage());
    }
  }
}
