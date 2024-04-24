package idatt2106.systemutvikling.sparesti.mockBank.mapper;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;

public class MockBankTransactionMapper {


    public static Transaction toModel(TransactionDAO dao) {
        return new Transaction(
                dao.getTransactionId(),
                dao.getAccountDAO().getAccountNr(),
                dao.getTransactionTitle(),
                dao.getTime(),
                dao.getDebtorAccount(),
                dao.getDebtorName(),
                dao.getCreditorAccount(),
                dao.getCreditorName(),
                dao.getAmount(),
                dao.getCurrency(),
                TransactionCategory.NOT_CATEGORIZED
        );
    }
}
