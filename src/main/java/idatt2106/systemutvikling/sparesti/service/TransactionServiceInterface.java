package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;

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

    /**
     * Creates a transaction with the given details and returns the created transaction DAO.
     *
     * @param debtorName The name of the debtor.
     * @param creditorName The name of the creditor.
     * @param transactionTitle The title of the transaction.
     * @param debtorAccount The account number of the debtor.
     * @param creditorAccount The account number of the creditor.
     * @param amount The amount of the transaction.
     * @param currency The currency of the transaction.
     * @return TransactionDAO The transaction DAO representing the created transaction.
     */
    TransactionDAO createTransaction(String debtorName, String creditorName, String transactionTitle, Long debtorAccount, Long creditorAccount, Long amount, String currency);
}
