package idatt2106.systemutvikling.sparesti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

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
  private String birthDate;
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
  private Boolean isConnectedToBank;

}
