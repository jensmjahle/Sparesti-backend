package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;

import java.util.Date;

/**
 * Mapper class for the Transaction entity.
 */
public class TransactionMapper {

    /**
     * Maps a TransactionDAO to a Transaction.
     *
     * @param t the TransactionDAO to map
     * @return the Transaction
     */
    public static TransactionDTO toDTO(Transaction t) {
        TransactionDTO dto = new TransactionDTO();

        dto.setTransactionId(t.getTransactionId());
        dto.setAmount(t.getAmount());
        dto.setTime(t.getTime());
        dto.setCurrency(t.getCurrency());
        dto.setTransactionTitle(t.getTransactionTitle());
        dto.setCreditorAccount(t.getCreditorAccount());
        dto.setCreditorName(t.getCreditorName());
        dto.setDebtorAccount(t.getDebtorAccount());
        dto.setDebtorName(t.getDebtorName());
        dto.setTransactionCategory(t.getCategory());

        return dto;
    }
}
