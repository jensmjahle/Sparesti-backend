package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dto.BankAccountDTO;
import idatt2106.systemutvikling.sparesti.model.BankAccount;

public class BankAccountMapper {

    /**
     * Maps a BankAccount to a BankAccountDTO.
     *
     * @param b the BankAccount to map
     * @return the BankAccountDTO
     */
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
