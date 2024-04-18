package idatt2106.systemutvikling.sparesti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data transfer object for User
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private String profilePictureBase64;
  private Long monthlyIncome;
  private Long monthlySavings;
  private Long monthlyFixedExpenses;
  private Long currentAccount;
  private Long savingsAccount;
  private Boolean isConnectedToBank;

}
