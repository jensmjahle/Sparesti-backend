package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

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

  // Add similar tests for other condition types
}
