package idatt2106.systemutvikling.sparesti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
@Getter
@Setter
@NoArgsConstructor
public class UserCredentialsDTO {
  private String username;
  private String password;
  @ Nullable
  private String email;
  @ Nullable
  private String firstName;
  @ Nullable
  private String lastName;
  @ Nullable
  private String birthDate;
}
