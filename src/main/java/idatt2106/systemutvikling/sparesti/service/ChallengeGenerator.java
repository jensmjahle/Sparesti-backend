package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;

public interface ChallengeGenerator {
  void generateDailyChallenges(UserDAO userDAO);
void generateWeeklyChallenges(UserDAO userDAO);
void generateMonthlyChallenges(UserDAO userDAO);
void generateRandomChallenges(UserDAO userDAO);
void generateInstantChallenge(UserDAO userDAO);
}
