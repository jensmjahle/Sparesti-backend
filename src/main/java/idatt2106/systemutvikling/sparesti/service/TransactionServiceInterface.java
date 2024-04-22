package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionServiceInterface {

    /**
     * Retrieves all outgoing transactions for a specific account.
     * @param accountNumber The account number of the account.
     * @param page The page number in the pagination scheme.
     * @param pageSize The size of each page in the pagination scheme.
     * @return A List of a page of outgoing transactions from the specified account.
     */
    List<Transaction> getLatestExpensesForAccountNumber(Long accountNumber, int page, int pageSize);
}
