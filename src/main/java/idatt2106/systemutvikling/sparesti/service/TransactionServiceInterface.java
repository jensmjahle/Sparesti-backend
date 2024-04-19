package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;

import java.util.List;

public interface TransactionServiceInterface {

    /**
     * Retrieves all transactions with outgoing funds.
     * @param accountNumber The number id of the selected account.
     * @param page The page number in the pagination scheme.
     * @param pageSize The size of each page in the pagination scheme.
     * @return A List of a page of outgoing transactions from the specified account.
     */
    List<TransactionDAO> getLatestExpensesForAccountNumber(Long accountNumber, int page, int pageSize);
}
