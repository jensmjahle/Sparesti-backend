package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ConditionServiceTest {

  @Mock
  private MilestoneLogRepository milestoneLogRepository;

  @Mock
  private MilestoneRepository milestoneRepository;

  @Mock
  private ChallengeLogRepository challengeLogRepository;

  @InjectMocks
  private ConditionService conditionService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testIsConditionMet_MilestonesConditionMet_ReturnsTrue() {
    // Mocking data
    when(milestoneLogRepository.count()).thenReturn(5L);

    // Create a condition object for milestones
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.MILESTONES);
    condition.setQuantity(5L);

    // Test the method
    assertTrue(conditionService.isConditionMet(condition));
  }

  @Test
  public void testIsConditionMet_MilestonesConditionNotMet_ReturnsFalse() {
    // Mocking data
    when(milestoneLogRepository.count()).thenReturn(3L);

    // Create a condition object for milestones
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.MILESTONES);
    condition.setQuantity(5L);

    // Test the method
    assertFalse(conditionService.isConditionMet(condition));
  }

  @Test
  public void testIsConditionMet_ChallengesConditionMet_ReturnsTrue() {
    // Mocking data
    when(challengeLogRepository.findAll()).thenReturn(Arrays.asList(
            createChallengeLogDAO(10L, 10L),
            createChallengeLogDAO(20L, 20L),
            createChallengeLogDAO(30L, 30L)
    ));

    // Create a condition object for challenges
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.CHALLENGES);
    condition.setQuantity(3L);

    // Test the method
    assertTrue(conditionService.isConditionMet(condition));
  }

  @Test
  public void testIsConditionMet_ChallengesConditionNotMet_ReturnsFalse() {
    // Mocking data
    when(challengeLogRepository.findAll()).thenReturn(Arrays.asList(
            createChallengeLogDAO(10L, 10L),
            createChallengeLogDAO(20L, 20L)
    ));

    // Create a condition object for challenges
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.CHALLENGES);
    condition.setQuantity(3L);

    // Test the method
    assertFalse(conditionService.isConditionMet(condition));
  }

  @Test
  public void testIsConditionMet_SavingsConditionMet_ReturnsTrue() {
    // Mocking data
    when(milestoneLogRepository.findAll()).thenReturn(Collections.singletonList(
            createMilestoneLogDAO(50L)
    ));
    when(milestoneRepository.findAll()).thenReturn(Collections.singletonList(
            createMilestoneDAO(50L)
    ));

    // Create a condition object for savings
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.SAVINGS);
    condition.setQuantity(100L);

    // Test the method
    assertTrue(conditionService.isConditionMet(condition));
  }

  @Test
  public void testIsConditionMet_SavingsConditionNotMet_ReturnsFalse() {
    // Mocking data
    when(milestoneLogRepository.findAll()).thenReturn(Collections.singletonList(
            createMilestoneLogDAO(30L)
    ));
    when(milestoneRepository.findAll()).thenReturn(Collections.singletonList(
            createMilestoneDAO(30L)
    ));

    // Create a condition object for savings
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.SAVINGS);
    condition.setQuantity(100L);

    // Test the method
    assertFalse(conditionService.isConditionMet(condition));
  }

  private ChallengeLogDAO createChallengeLogDAO(Long goalSum, Long achievedSum) {
    ChallengeLogDAO challengeLogDAO = new ChallengeLogDAO();
    challengeLogDAO.setGoalSum(goalSum);
    challengeLogDAO.setChallengeAchievedSum(achievedSum);
    return challengeLogDAO;
  }

  private MilestoneLogDAO createMilestoneLogDAO(Long achievedSum) {
    MilestoneLogDAO milestoneLogDAO = new MilestoneLogDAO();
    milestoneLogDAO.setMilestoneAchievedSum(achievedSum);
    return milestoneLogDAO;
  }

  private MilestoneDAO createMilestoneDAO(Long currentSum) {
    MilestoneDAO milestoneDAO = new MilestoneDAO();
    milestoneDAO.setMilestoneCurrentSum(currentSum);
    return milestoneDAO;
  }
}
