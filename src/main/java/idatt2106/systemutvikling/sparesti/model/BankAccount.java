package idatt2106.systemutvikling.sparesti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for the BankAccount entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    private Long accountNr;

    private String username;

    private Long balance;

    private String name;

    private String type;

    private String currency;
}
