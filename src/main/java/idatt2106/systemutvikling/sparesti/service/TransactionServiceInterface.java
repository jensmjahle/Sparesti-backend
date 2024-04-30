package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionServiceInterface {

    /**
     * Retrieves all outgoing transactions for a specific account.
     * A transaction is considered outgoing when it moves currency from the specified account to any account
     * not owned by the owner of the specified account. A transaction is not considered outgoing if it is an internal
     * transaction; moving currency between accounts owned by the same user.
     * The implementation of this method is expected to fetch all transactions from now until 'dateLimit'
     * where the specified accountNumber is either the debtor or creditor. Then, it will filter out any transactions that
     * are either internal or incoming (relative to the account's owner). This is the easiest form of implementation
     * when connecting to a standard PSD2 API.
     * @param accountNumber The account number of the specified account.
     * @param dateLimit Transactions older than this date will not be fetched.
     * @return A List of transactions moving currency OUT of the specified account.
     */
    List<Transaction> getLatestExpensesForAccountNumber(Long accountNumber, Date dateLimit);

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
    Boolean createTransaction(String debtorName, String creditorName, String transactionTitle, Long debtorAccount, Long creditorAccount, Long amount, String currency);
}
