package idatt2106.systemutvikling.sparesti.task;

import idatt2106.systemutvikling.sparesti.service.challengeGeneration.GenerateChallengeService;
import idatt2106.systemutvikling.sparesti.tasks.GenerateChallengeTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenerateChallengeTaskTest {

  @Mock
  private GenerateChallengeService generateChallengeService;

  @InjectMocks
  private GenerateChallengeTask generateChallengeTask;

  @Test
  void testDailyChallenges() {
    generateChallengeTask.dailyChallenges();
    Mockito.verify(generateChallengeService).generateDailyChallenges();
  }

  @Test
  void testWeeklyChallenges() {
    generateChallengeTask.weeklyChallenges();
    Mockito.verify(generateChallengeService).generateWeeklyChallenges();
  }

  @Test
  void testMonthlyChallenges() {
    generateChallengeTask.monthlyChallenges();
    Mockito.verify(generateChallengeService).generateMonthlyChallenges();
  }

  @Test
  void testRandomChallenges() {
    generateChallengeTask.randomChallenges();
    Mockito.verify(generateChallengeService).generateRandomChallenges();
  }
}
