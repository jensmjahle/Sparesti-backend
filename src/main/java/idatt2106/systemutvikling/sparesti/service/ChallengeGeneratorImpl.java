package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import java.time.LocalDateTime;
import java.util.List;

public class ChallengeGeneratorImpl implements ChallengeGenerator {

  @Override
  public void generateDailyChallenges(UserDAO userDAO) {
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(1);


    // Generate daily challenges
  }
@Override
  public void generateWeeklyChallenges(UserDAO userDAO) {
    // Generate weekly challenges
  }
@Override
  public void generateMonthlyChallenges(UserDAO userDAO) {
    // Generate monthly challenges
  }
@Override
  public void generateRandomChallenges(UserDAO userDAO) {
    // Generate random challenges
  }

  @Override
  public void generateInstantChallenge(UserDAO userDAO) {
    // Generate instant challenge
  }

}
