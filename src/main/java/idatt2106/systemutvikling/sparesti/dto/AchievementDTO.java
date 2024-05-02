package idatt2106.systemutvikling.sparesti.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object for Achievement
 */
@Getter
@Setter
public class AchievementDTO {

  @NotNull
  private Long achievementId;

  private String achievementTitle;
  private String achievementDescription;
  private String badge;
}
