package idatt2106.systemutvikling.sparesti.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object for Milestone
 * Used to transfer milestone data between layers
 */
@Getter
@Setter
public class MilestoneDTO {
  @NotNull
private Long milestoneId;
private String username;
private String milestoneTitle;
private String milestoneDescription;
private Long milestoneGoalSum;
private Long milestoneCurrentSum;
private String milestoneImage;
private LocalDateTime deadlineDate;
private LocalDateTime startDate;

}
