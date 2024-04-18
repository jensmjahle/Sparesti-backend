package idatt2106.systemutvikling.sparesti.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ChallengeDTOTest {

  private ChallengeDTO challengeDTO;
  private final static Long challengeId = 1L;
  private final static String username = "username";
  private final static String challengeTitle = "Test Challenge";
  private final static String challengeDescription = "Test challengeDescription";
  private final static Long challengeGoalSum = 100L;
  private final static Long challengeCurrentSum = 70L;

  private final static boolean isActive = true;

  private final static Long recurring = 10L;
  private final LocalDateTime deadlineDate = LocalDateTime.of(2024, 5, 15, 0, 0);
  private final LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);

  @BeforeEach
  public void setUp() {
    challengeDTO = new ChallengeDTO();
    challengeDTO.setChallengeId(challengeId);
    challengeDTO.setUsername(username);
    challengeDTO.setChallengeTitle(challengeTitle);
    challengeDTO.setChallengeDescription(challengeDescription);
    challengeDTO.setCurrentSum(challengeCurrentSum);
    challengeDTO.setGoalSum(challengeGoalSum);
    challengeDTO.setStartDate(startDate);
    challengeDTO.setExpirationDate(deadlineDate);
    challengeDTO.setRecurring(recurring);
    challengeDTO.setActive(isActive);
  }

  @Test
  @DisplayName("MilestoneDTO is correctly instantiated")
  public void testMilestoneDTOFields() {
    assertNotNull(challengeDTO);
    assertEquals(challengeId, challengeDTO.getChallengeId());
    assertEquals(username, challengeDTO.getUsername());
    assertEquals(challengeTitle, challengeDTO.getChallengeTitle());
    assertEquals(challengeDescription, challengeDTO.getChallengeDescription());
    assertEquals(challengeGoalSum, challengeDTO.getGoalSum());
    assertEquals(challengeCurrentSum, challengeDTO.getCurrentSum());
    assertEquals(recurring, challengeDTO.getRecurring());
    assertEquals(startDate, challengeDTO.getStartDate());
    assertEquals(deadlineDate, challengeDTO.getExpirationDate());
    assertEquals(isActive, challengeDTO.isActive());
  }
}
