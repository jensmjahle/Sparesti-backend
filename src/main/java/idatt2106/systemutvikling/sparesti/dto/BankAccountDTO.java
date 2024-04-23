package idatt2106.systemutvikling.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {

    private Long accountNumber;

    private String username;

    private Long balance;

    private String name;

    private String type;

    private String currency;
}
