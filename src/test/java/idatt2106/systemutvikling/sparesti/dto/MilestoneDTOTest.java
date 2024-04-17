package idatt2106.systemutvikling.sparesti.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class MilestoneDTOTest {

  private MilestoneDTO milestoneDTO;
  private final Long milestoneId = 1L;
  private final String username = "testUser";
  private final String milestoneTitle = "Test Milestone";
  private final String milestoneDescription = "Test Milestone Description";
  private final Long milestoneGoalSum = 1000L;
  private final Long milestoneCurrentSum = 500L;
  private final String milestoneImage = "test_image.jpg";
  private final LocalDateTime deadlineDate = LocalDateTime.of(2024, 4, 20, 0, 0);
  private final LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);

  @BeforeEach
  public void setUp() {
    milestoneDTO = new MilestoneDTO();
    milestoneDTO.setMilestoneId(milestoneId);
    milestoneDTO.setUsername(username);
    milestoneDTO.setMilestoneTitle(milestoneTitle);
    milestoneDTO.setMilestoneDescription(milestoneDescription);
    milestoneDTO.setMilestoneGoalSum(milestoneGoalSum);
    milestoneDTO.setMilestoneCurrentSum(milestoneCurrentSum);
    milestoneDTO.setMilestoneImage(milestoneImage);
    milestoneDTO.setDeadlineDate(deadlineDate);
    milestoneDTO.setStartDate(startDate);
  }

  @Test
  @DisplayName("MilestoneDTO is correctly instantiated")
  public void testMilestoneDTOFields() {
    assertNotNull(milestoneDTO);
    assertEquals(milestoneId, milestoneDTO.getMilestoneId());
    assertEquals(username, milestoneDTO.getUsername());
    assertEquals(milestoneTitle, milestoneDTO.getMilestoneTitle());
    assertEquals(milestoneDescription, milestoneDTO.getMilestoneDescription());
    assertEquals(milestoneGoalSum, milestoneDTO.getMilestoneGoalSum());
    assertEquals(milestoneCurrentSum, milestoneDTO.getMilestoneCurrentSum());
    assertEquals(milestoneImage, milestoneDTO.getMilestoneImage());
    assertEquals(deadlineDate, milestoneDTO.getDeadlineDate());
    assertEquals(startDate, milestoneDTO.getStartDate());
  }
}
