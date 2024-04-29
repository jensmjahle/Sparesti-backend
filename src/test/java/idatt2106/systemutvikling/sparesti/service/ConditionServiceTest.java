package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.*;
import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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

    mockCurrentUsername();
  }

  private void mockCurrentUsername() {
    // Create a mock Authentication object with the specified username
    Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", null);

    // Set the mock Authentication object into SecurityContextHolder
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
  }

  @Test
  void testIsConditionMet_MilestonesConditionMet_ReturnsTrue() {

    List<MilestoneLogDAO> milestoneLogs = new ArrayList<>();
    milestoneLogs.add(new MilestoneLogDAO());
    milestoneLogs.add(new MilestoneLogDAO());
    milestoneLogs.add(new MilestoneLogDAO());

    // Stubbing the milestoneLogRepository method to return the mocked milestoneLogs
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(null))
        .thenReturn(milestoneLogs);

    // Creating a condition where quantity is less than or equal to the number of milestone logs
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.MILESTONES);
    condition.setQuantity(2L); // For example, checking if quantity <= 3 (number of milestone logs)

    // Executing the method under test
    boolean result = conditionService.isConditionMet(condition);

    // Validating the result
    assertTrue(result); // Since 2 <= 3, it should return true
  }

  @Test
  void testIsConditionMet_MilestonesConditionNotMet_ReturnsFalse() {
    List<MilestoneLogDAO> milestoneLogs = new ArrayList<>();
    milestoneLogs.add(new MilestoneLogDAO());
    milestoneLogs.add(new MilestoneLogDAO());
    milestoneLogs.add(new MilestoneLogDAO());

    // Stubbing the milestoneLogRepository method to return the mocked milestoneLogs
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(null))
        .thenReturn(milestoneLogs);

    // Creating a condition where quantity is less than or equal to the number of milestone logs
    ConditionDAO condition = new ConditionDAO();
    condition.setConditionType(ConditionType.MILESTONES);
    condition.setQuantity(4L); // For example, checking if quantity <= 3 (number of milestone logs)

    // Executing the method under test
    boolean result = conditionService.isConditionMet(condition);

    // Validating the result
    assertFalse(result); // Since 2 <= 3, it should return true
  }

  @Test
  void testIsConditionMet_ChallengesConditionMet_ReturnsTrue() {
    // Mocking data
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(null)).thenReturn(Arrays.asList(
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
  void testIsConditionMet_ChallengesConditionNotMet_ReturnsFalse() {
    // Mocking data
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(null)).thenReturn(Arrays.asList(
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
  void testIsConditionMet_SavingsConditionMet_ReturnsTrue() {
    // Mocking data
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(null)).thenReturn(Collections.singletonList(
            createMilestoneLogDAO(50L)
    ));
    when(milestoneRepository.findMilestoneDAOByUserDAO_Username(null)).thenReturn(Collections.singletonList(
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
  void testIsConditionMet_SavingsConditionNotMet_ReturnsFalse() {
    // Mocking data
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(null)).thenReturn(Collections.singletonList(
        createMilestoneLogDAO(40L)
    ));
    when(milestoneRepository.findMilestoneDAOByUserDAO_Username(null)).thenReturn(Collections.singletonList(
        createMilestoneDAO(50L)
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
