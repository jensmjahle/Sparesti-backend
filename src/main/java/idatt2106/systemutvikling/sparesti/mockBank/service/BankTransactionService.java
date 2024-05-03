package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.mapper.MockBankTransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
   * will trick the system to think that the date is 30th of March 2024.
   *
   * @param accountNumber The account number of the specified account.
   * @param dateLimit     Transactions older than this date will not be fetched.
   * @return {@link List}  The list of {@link Transaction}.
   */
  @Override
  public List<Transaction> getLatestExpensesForAccountNumber(Long accountNumber, Date dateLimit) {
    List<AccountDAO> allAccountsForUser = accountService.findOtherAccountsOwnedBySameUser(
        accountNumber);
    List<Long> accounts = allAccountsForUser.stream().map(AccountDAO::getAccountNr).toList();

    List<TransactionDAO> fetchedTransactions;
    if (mockProperties.isEnabled()) {
      // Use fixed date of 30th March 2024 and subtract 30 days
      LocalDate fixedDate = LocalDate.of(2024, 3, 30);
      Date date30DaysAgo = Date.from(
          fixedDate.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
      fetchedTransactions = transactionRepository.findByAccountDAO_AccountNrAndTimeAfterOrderByTimeDesc(
          accountNumber, date30DaysAgo);
    } else {
      fetchedTransactions = transactionRepository.findByAccountDAO_AccountNrAndTimeAfterOrderByTimeDesc(
          accountNumber, dateLimit);
    }

    return fetchedTransactions.stream()
        .filter((TransactionDAO t) -> t.getDebtorAccount().equals(accountNumber))
        .filter((TransactionDAO t) -> accounts.stream()
            .noneMatch((Long acId) -> t.getCreditorAccount().equals(acId)))
        .map(MockBankTransactionMapper::toModel)
        .toList();
  }

  /**
   * Creates a transaction and saves it to the database.
   *
   * @param debtorName       The name of the debtor.
   * @param creditorName     The name of the creditor.
   * @param transactionTitle The title of the transaction.
   * @param debtorAccount    The account number of the debtor.
   * @param creditorAccount  The account number of the creditor.
   * @param amount           The amount of the transaction.
   * @param currency         The currency of the transaction.
   * @return TransactionDAO The saved transaction DAO.
   */
  public Boolean createTransaction(String debtorName, String creditorName, String transactionTitle,
      Long debtorAccount, Long creditorAccount, Long amount, String currency) {
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
