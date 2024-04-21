package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.ChallengeData;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateChallengeService {

  private final UserRepository userRepository;
  private final Logger logger = Logger.getLogger(GenerateChallengeService.class.getName());
  private ChallengeGenerator challengeGenerator;

  @Autowired
  public GenerateChallengeService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Generates one daily challenge for all users in the database. This method is called once a day
   * by GenerateChallengeTask.
   */
  public void generateDailyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateDailyChallenges(initializeChallengeData(user));
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
          challengeGenerator.generateWeeklyChallenges(initializeChallengeData(user));
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
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateMonthlyChallenges(initializeChallengeData(user));
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
          challengeGenerator.generateRandomChallenges(initializeChallengeData(user));
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
   * Generates an instant challenge for a user.
   *
   * @param user The user to generate the challenge for.
   */
  public void generateInstantChallenge(UserDAO user) {
    try {
      challengeGenerator.generateInstantChallenge(initializeChallengeData(user));
    } catch (Exception e) {
      logger.severe("Failed to generate instant challenge for user " + user.getUsername() + "."
          + e.getMessage());
    }
  }

  //todo: implement initializeChallengeData. Retrieve all necessary data from the database and return a ChallengeData object.
  public ChallengeData initializeChallengeData(UserDAO user) {

    UserDAO userDAO;
    List<ChallengeDAO> activeChallenges;
    List<ChallengeDAO> inactiveChallenges;
    int activeChallengesCount;
    int inactiveChallengesCount;
    double challengeCompletionRate;
    double challengeAcceptanceRate;
    double thisMonthTotalSavings;
    double monthlySavingsGoal;
    double monthlyIncome;
    double monthlyFixedExpenses;
    LocalDateTime startOfThisMonth;
    LocalDateTime endOfThisMonth;
    LocalDateTime currentDateTime;
    List<Transaction> transactions;
    Map<TransactionCategory, Double> categoryExpensesRatio;
    Map<TransactionCategory, Double> pastChallengesByCategoryPercentage;
    Map<TransactionCategory, Double> pastChallengesByCategoryAcceptedPercentage;

    return null;
  }
}
