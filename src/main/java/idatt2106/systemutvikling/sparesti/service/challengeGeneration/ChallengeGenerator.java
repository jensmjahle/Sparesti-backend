package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.model.ChallengeData;

public interface ChallengeGenerator {

  void generateDailyChallenges(ChallengeData challengeData);

  void generateWeeklyChallenges(ChallengeData challengeData);

  void generateMonthlyChallenges(ChallengeData challengeData);

  void generateRandomChallenges(ChallengeData challengeData);

  void generateInstantChallenge(ChallengeData challengeData);
}
