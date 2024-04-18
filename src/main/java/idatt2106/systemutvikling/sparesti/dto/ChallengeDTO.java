package idatt2106.systemutvikling.sparesti.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * A Data Transfer Object representing a challenge
 */
@Getter
@Setter
public class ChallengeDTO {
  @NotNull
  private Long challengeId;

  private String username;

  private String challengeTitle;

  private String challengeDescription;

  private Long goalSum;

  private Long currentSum;

  private LocalDateTime startDate;

  private LocalDateTime expirationDate;

  private Long recurring;

  private boolean isActive;
}
