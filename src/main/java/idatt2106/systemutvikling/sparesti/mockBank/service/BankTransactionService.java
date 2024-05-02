package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.mapper.MockBankTransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for the BankTransaction entity.
 */
@Service
@AllArgsConstructor
public class BankTransactionService implements TransactionServiceInterface {

  private final Logger logger = Logger.getLogger(BankTransactionService.class.getName());
  private final TransactionRepository transactionRepository;
  private final MockBankAccountService accountService;
  private final MockDataConfig mockProperties;

  /**
   * Fetches the latest transactions for the specified account. For the sake of testing the method
   * will return all transactions for the specified account not based of any date.
   *
   * @param accountNumber The account number of the specified account.
   * @param dateLimit Transactions older than this date will not be fetched.
   * @return List<Transaction> The list of transactions.
   */
  @Override
  public List<Transaction> getLatestExpensesForAccountNumber(Long accountNumber, Date dateLimit) {
    List<AccountDAO> allAccountsForUser = accountService.findOtherAccountsOwnedBySameUser(accountNumber);
    List<Long> accounts = allAccountsForUser.stream().map(AccountDAO::getAccountNr).toList();

    List<TransactionDAO> fetchedTransactions;
    if (mockProperties.isEnabled()) {
      fetchedTransactions = transactionRepository.findByAccountDAO_AccountNr(accountNumber);
    } else {
      fetchedTransactions = transactionRepository.findByAccountDAO_AccountNrAndTimeAfter(accountNumber, dateLimit);
    }

    return fetchedTransactions.stream()
            .filter((TransactionDAO t) -> t.getDebtorAccount().equals(accountNumber))
            .filter((TransactionDAO t) -> accounts.stream().noneMatch((Long acId) -> t.getCreditorAccount().equals(acId)))
            .map(MockBankTransactionMapper::toModel)
            .toList();
  }

  /**
   * Creates a transaction and saves it to the database.
   *
   * @param debtorName The name of the debtor.
   * @param creditorName The name of the creditor.
   * @param transactionTitle The title of the transaction.
   * @param debtorAccount The account number of the debtor.
   * @param creditorAccount The account number of the creditor.
   * @param amount The amount of the transaction.
   * @param currency The currency of the transaction.
   * @return TransactionDAO The saved transaction DAO.
   */
  public Boolean createTransaction(String debtorName, String creditorName, String transactionTitle, Long debtorAccount, Long creditorAccount, Long amount, String currency) {
    try {
      TransactionDAO transactionDAO = new TransactionDAO();

      transactionDAO.setAccountDAO(accountService.findAccountByAccountNr(debtorAccount));
      transactionDAO.setTransactionTitle(transactionTitle);
      transactionDAO.setTime(new Date());
      transactionDAO.setDebtorAccount(debtorAccount);
      transactionDAO.setDebtorName(debtorName);
      transactionDAO.setCreditorAccount(creditorAccount);
      transactionDAO.setCreditorName(creditorName);
      transactionDAO.setAmount(amount);
      transactionDAO.setCurrency(currency);

      transactionRepository.save(transactionDAO);
      return true;
    } catch (Exception e) {
      logger.severe("Something went wrong when making transaction");
      return false;
    }
  }
}
