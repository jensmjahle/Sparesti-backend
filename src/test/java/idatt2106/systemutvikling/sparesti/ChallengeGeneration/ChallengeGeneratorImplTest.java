package idatt2106.systemutvikling.sparesti.ChallengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeData;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.service.OpenAIService;
import idatt2106.systemutvikling.sparesti.service.challengeGeneration.ChallengeGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ChallengeGeneratorImplTest {

  @InjectMocks
  private ChallengeGeneratorImpl challengeGenerator;

  @Mock
  private OpenAIService openAIService;

  @Mock
  private ChallengeRepository challengeRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateChallenge() {
    // Create a mock of ChallengeData
    ChallengeData mockData = mock(ChallengeData.class);

    // Define the return values of the methods when they are called
    when(mockData.getInactChalCount()).thenReturn(1);
    when(mockData.getChalComplRate()).thenReturn(0.5);
    when(mockData.getMthSavGoal()).thenReturn(1000.0);
    when(mockData.getThisMthTotSav()).thenReturn(500.0);
    when(mockData.getThisMthPLNDSav()).thenReturn(500.0);
    when(mockData.getToday()).thenReturn(LocalDateTime.now());
    when(mockData.getEndOfMth()).thenReturn(LocalDateTime.now().plusMonths(1));
    when(mockData.getStartOfMth()).thenReturn(LocalDateTime.now().minusMonths(1));
    when(mockData.getCatExpRatio()).thenReturn(new HashMap<>());
    when(mockData.getPastChalByCatRatio()).thenReturn(new HashMap<>());
    when(mockData.getPastChalByCatAccRatio()).thenReturn(new HashMap<>());
    when(mockData.getTrxs()).thenReturn(new ArrayList<Transaction>());
    when(mockData.getUser()).thenReturn(new UserDAO());

    int duration = 7;

    challengeGenerator.generateChallenge(mockData, duration);

    verify(challengeRepository, times(1)).save(any(ChallengeDAO.class));
  }
}