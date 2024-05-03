package idatt2106.systemutvikling.sparesti.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for Milestone Used to transfer milestone data between layers
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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
