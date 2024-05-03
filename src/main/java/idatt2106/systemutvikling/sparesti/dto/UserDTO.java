package idatt2106.systemutvikling.sparesti.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Data transfer object for User
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

  private String username;
  @Nullable
  private String email;
  @Nullable
  private String firstName;
  @Nullable
  private String lastName;
  @Nullable
  private LocalDate birthDate;
  @Nullable
  private String profilePictureBase64;
  @Nullable
  private Long monthlyIncome;
  @Nullable
  private Long monthlySavings;
  @Nullable
  private Long monthlyFixedExpenses;
  @Nullable
  private Long currentAccount;
  @Nullable
  private Long savingsAccount;
  @Nullable
  private List<AchievementDTO> achievementDTOList;
  private Boolean isConnectedToBank;

}
