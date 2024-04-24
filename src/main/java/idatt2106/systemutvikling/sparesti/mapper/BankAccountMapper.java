package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dto.BankAccountDTO;
import idatt2106.systemutvikling.sparesti.model.BankAccount;

public class BankAccountMapper {

    public static BankAccountDTO toDTO(BankAccount b) {
        return new BankAccountDTO(
                b.getAccountNr(),
                b.getUsername(),
                b.getBalance(),
                b.getName(),
                b.getType(),
                b.getCurrency()
        );
    }
}
