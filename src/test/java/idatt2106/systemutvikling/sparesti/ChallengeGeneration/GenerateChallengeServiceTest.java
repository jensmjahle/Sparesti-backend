package idatt2106.systemutvikling.sparesti.ChallengeGeneration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeData;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.service.ChallengeLogService;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.OpenAIService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import idatt2106.systemutvikling.sparesti.service.challengeGeneration.ChallengeGeneratorImpl;
import idatt2106.systemutvikling.sparesti.service.challengeGeneration.GenerateChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class GenerateChallengeServiceTest {

  @Mock
  private ChallengeGeneratorImpl challengeGenerator;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ChallengeService challengeService;

  @Mock
  private ChallengeLogService challengeLogService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private OpenAIService openAIService;

  @Mock
  private ChallengeRepository challengeRepository;

  @InjectMocks
  private GenerateChallengeService generateChallengeService;

  @Captor
  private ArgumentCaptor<ChallengeData> challengeDataCaptor;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateDailyChallenges() {
    UserDAO user = new UserDAO();
    user.setUsername("testUser");
    user.setMonthlySavings(1000L);
    user.setMonthlyIncome(5000L);
    user.setMonthlyFixedExpenses(2000L);
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    generateChallengeService.generateDailyChallenges();

    verify(challengeGenerator).generateChallenge(challengeDataCaptor.capture(), eq(1));
    ChallengeData challengeData = challengeDataCaptor.getValue();
    assertEquals("testUser", challengeData.getUser().getUsername());
  }

  @Test
  public void testGenerateWeeklyChallenges() {
    UserDAO user = new UserDAO();
    user.setUsername("testUser");
    user.setUsername("testUser");
    user.setMonthlySavings(1000L);
    user.setMonthlyIncome(5000L);
    user.setMonthlyFixedExpenses(2000L);
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    generateChallengeService.generateWeeklyChallenges();

    verify(challengeGenerator).generateChallenge(challengeDataCaptor.capture(), eq(7));
    ChallengeData challengeData = challengeDataCaptor.getValue();
    assertEquals("testUser", challengeData.getUser().getUsername());
  }

  @Test
  public void testGenerateMonthlyChallenges() {
    UserDAO user = new UserDAO();
    user.setUsername("testUser");
    user.setUsername("testUser");
    user.setMonthlySavings(1000L);
    user.setMonthlyIncome(5000L);
    user.setMonthlyFixedExpenses(2000L);
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    generateChallengeService.generateMonthlyChallenges();

    verify(challengeGenerator).generateChallenge(challengeDataCaptor.capture(), any(Integer.class));
    ChallengeData challengeData = challengeDataCaptor.getValue();
    assertEquals("testUser", challengeData.getUser().getUsername());
  }

  @Test
  public void testGenerateRandomChallenges() {
    UserDAO user = new UserDAO();
    user.setUsername("testUser");
    user.setUsername("testUser");
    user.setMonthlySavings(1000L);
    user.setMonthlyIncome(5000L);
    user.setMonthlyFixedExpenses(2000L);
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    generateChallengeService.generateRandomChallenges();

    verify(challengeGenerator).generateChallenge(challengeDataCaptor.capture(), eq(1));
    ChallengeData challengeData = challengeDataCaptor.getValue();
    assertEquals("testUser", challengeData.getUser().getUsername());
  }

  @Test
  public void testCalcAmount() throws Exception {
    // Arrange
    ChallengeGeneratorImpl challengeGenerator = new ChallengeGeneratorImpl(openAIService, challengeRepository);
    int min = 10;
    int max = 500;
    int duration = 7;
    double mthSavGoal = 1000;
    double thisMthTotSav = 500;
    double thisMthPLNDSav = 500;
    long daysToEndOfMonth = 10L; // replace with actual value
    long daysInMonth = 30L; // replace with actual value

    // Use reflection to access the private method
    Method method = ChallengeGeneratorImpl.class.getDeclaredMethod("calcAmount", int.class, int.class, int.class, double.class, double.class, double.class, long.class, long.class);
    method.setAccessible(true); // This line is necessary if the method is private

    // Act
    double result = (double) method.invoke(challengeGenerator, min, max, duration, mthSavGoal, thisMthTotSav, thisMthPLNDSav, daysToEndOfMonth, daysInMonth);

    // Assert
    assertTrue(result >= min && result <= max);
  }
}