package idatt2106.systemutvikling.sparesti.service;


import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.exceptions.UserNotFoundException;
import idatt2106.systemutvikling.sparesti.mapper.KeywordMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;

import java.util.Date;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

  private static final boolean DISABLE_OPENAI_PROMPTS = true;

  public static final Date DEFAULT_EXPENSES_TIME_SPAN = new Date(System.currentTimeMillis() - 30L *24*60*60*1000);

  private final TransactionServiceInterface transactionSocket;
  private final TransactionCategoryCacheService cacheService;
  private final UserRepository dbUser;
  private final OpenAIService openAIService;
  private final Logger logger = Logger.getLogger(TransactionService.class.getName());

  /**
   * Creates a savings transfer for a user.
   *
   * @param amount   The amount to transfer.
   * @param username The username of the user.
   * @return ResponseEntity<TransactionDTO> The ResponseEntity containing the created transaction
   * DTO.
   */
  public ResponseEntity<Boolean> createSavingsTransferForUser(Long amount, String username) {
    try {
      UserDAO user = dbUser.findByUsername(username);

      if (user == null) {
        return ResponseEntity.ok(false);
      }

      transactionSocket.createTransaction(
          username,
          username,
          "Savings transfer",
          user.getCurrentAccount(),
          user.getSavingsAccount(),
          amount,
          "NOK");

      return ResponseEntity.ok(true);
    } catch (Exception e) {
      logger.severe("An error occurred while making savings transfer username " + username + ": "
          + e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }


  /**
   * Retrieves the outgoing transactions for a specific account from the past 30 days.
   * A transaction is considered outgoing when it moves
   * currency from the specified account to any account not owned by the owner of the specified account.
   * A transaction is not considered outgoing if it is an internal transaction; moving currency
   * between accounts owned by the same user.
   * @return A list of outgoing transactions for the specified account number.
   */
  public List<Transaction> getLatestExpensesForCurrentUser_CheckingAccount_Categorized() {
    return getLatestExpensesForCurrentUser_CheckingAccount_Categorized(DEFAULT_EXPENSES_TIME_SPAN);
  }


  /**
   * Retrieves the outgoing transactions for a specific account from the past 30 days. The transactions are
   * categorized using the OpenAI API.
   *
   * @param dateLimit Transactions older than this date will not be fetched.
   * @return A list of outgoing transactions for the specified account number.
   */
    public List<Transaction> getLatestExpensesForCurrentUser_CheckingAccount_Categorized(Date dateLimit) {
    List<Transaction> uncategorizedTransactions = getLatestExpensesForCurrentUser_CheckingAccount(dateLimit);

    List<Transaction> categorizedTransactions = categorizeTransactions(uncategorizedTransactions);

    return categorizedTransactions;
  }



  /** Retrieves all outgoing transactions of the checking account of the current user, from the last 30 days.
   * @param dateLimit Transactions older than this date will not be fetched.
   * @return A list of transactions.
   */
  public List<Transaction> getLatestExpensesForCurrentUser_CheckingAccount(Date dateLimit) {
    String username = CurrentUserService.getCurrentUsername();
    if (username == null)
      throw new UserNotFoundException("'CurrentUserService.getCurrentUsername()' returned " + username +
              " while retrieving latest expenses");

    return getLatestExpensesForUser_CheckingAccount(username, dateLimit);
  }



  /** Retrieves all outgoing transactions of the checking account of the specified user, from the last 30 days.
   * @param username The username of the user.
   * @param dateLimit Transactions older than this date will not be fetched.
   * @throws org.springframework.security.core.userdetails.UsernameNotFoundException If a user with the specified
   * username was not found in the database.
   * @return A list of transactions.
   */
  public List<Transaction> getLatestExpensesForUser_CheckingAccount(String username, Date dateLimit) {
    UserDAO user = dbUser.findByUsername(username);
    if (user == null)
      throw new UserNotFoundException();

    Long checkingAccount = user.getCurrentAccount();
    if (checkingAccount == null || checkingAccount == 0L)
      return null; // TODO: Throw exception?

    return getLatestExpensesForAccountNumber(checkingAccount, dateLimit);
  }



  /**
   * Retrieves all outgoing transactions for a specific account. A transaction is considered outgoing when it moves
   * currency from the specified account to any account not owned by the owner of the specified account.
   * A transaction is not considered outgoing if it is an internal transaction; moving currency
   * between accounts owned by the same user.
   * @param accountNumber The account number of the account to fetch expenses for.
   * @param dateLimit Transactions older than this date will not be fetched.
   * @return A list of outgoing transactions for the specified account number.
   */
  public List<Transaction> getLatestExpensesForAccountNumber(Long accountNumber, Date dateLimit) {
    return transactionSocket.getLatestExpensesForAccountNumber(accountNumber, dateLimit);
  }



  /**
   * Sets the category field of all transactions in the given list of transactions. This function
   * writes directly on the objects and does copy the objects. The returned object is just a
   * reference to the parameter object. The categorization first checks the categorization cache,
   * and uses the registered category if it finds an entry for the transaction. If none is found,
   * the transaction runs through the full categorization process, and the result is stored in the
   * cache.
   *
   * @param transactions The List-object containing the transactions to be categorized.
   * @return A reference to the parameter object.
   */
  public List<Transaction> categorizeTransactions(@NonNull List<Transaction> transactions) {
    for (Transaction t : transactions) {
      // Retrieve transaction category from cache
      TransactionCategoryDAO categoryDAO = cacheService.getCategoryFromCache(t.getTransactionId());

      // If the cache had no entry for the transaction
      if (categoryDAO == null) {
        // Calculate category for this new transaction
        TransactionCategory newCategory = categorizeTransaction(t);
        // Store an entry for this transaction in the cache
        categoryDAO = cacheService.setCategoryCache(t.getTransactionId(), newCategory, t.getTime());
      }

      // Set category of the transaction
      t.setCategory(categoryDAO.getTransactionCategory());
    }

    return transactions;
  }

  /**
   * Categorizes a transaction using the OpenAI API. If the transaction is not categorized, it is
   * categorized as OTHER.
   *
   * @param transaction The transaction to categorize.
   * @return The category of the transaction.
   */
  public TransactionCategory categorizeTransaction(Transaction transaction) {
    try {
      logger.info(
          "Categorizing transaction with title: " + transaction.getTransactionTitle() + ".");

      // CATEGORIZE TRANSACTION USING KEYWORDS
      TransactionCategory categoryKeyword = KeywordMapper.getCategory(
          transaction.getTransactionTitle());
      if (categoryKeyword != TransactionCategory.NOT_CATEGORIZED) {
        logger.info(
            "Transaction with title: " + transaction.getTransactionTitle() + " was categorized as: "
                + categoryKeyword + " using keywords.");
        return categoryKeyword;
      }

      if (DISABLE_OPENAI_PROMPTS) {
        return TransactionCategory.OTHER;
      }

      // CATEGORIZE TRANSACTION USING OPENAI
      String prompt = "Categorize this transaction into one of the following categories: "
          + "GROCERIES, RESTAURANT_AND_CAFE, TRANSPORTATION, SHOPPING, ENTERTAINMENT, INCOME, LOAN_DONATIONS. "
          + "If none of these categories apply, please categorize it as NOT_CATEGORIZED. Only respond with the category, nothing else. "
          + "Transaction: " + transaction
          + ".";

      try {
        String response = openAIService.chat(prompt);
        TransactionCategory categoryOpenAI = TransactionCategory.valueOf(response);

        if (categoryOpenAI != TransactionCategory.NOT_CATEGORIZED) {
          logger.info("Transaction with title: " + transaction.getTransactionTitle()
              + " was categorized as: " + categoryOpenAI + " using OpenAI.");
          return categoryOpenAI;
        }
      } catch (IllegalArgumentException e) {
        logger.severe(
            "Error categorizing transaction with title: " + transaction.getTransactionTitle()
                + " using OpenAI.");
      }

      // TRANSACTION NOT CATEGORIZED, CATEGORIZE AS OTHER
      logger.info("Transaction with title: " + transaction.getTransactionTitle()
          + " was not categorized. Categorizing as OTHER.");
      return TransactionCategory.OTHER;

    } catch (Exception e) {
      logger.severe(
          "Failed to categorize transaction with title: " + transaction.getTransactionTitle()
              + ".");
      throw e;
    }
  }

  /**
   * Creates a savings transfer for the current user. The transfer is made from the current account
   * to the savings account. If the user does not have a savings account, the transfer is not made.
   * The transfer is made with the title "Sparesti: Manual savings transfer towards savings goal".
   *
   * @param amount The amount to transfer.
   * @return ResponseEntity<TransactionDTO> The ResponseEntity containing the created transaction DTO.
   */
  public boolean createSavingsTransferForCurrentUser(long amount) {
    String username = CurrentUserService.getCurrentUsername();
    if (username == null) {
      return false;
    }

    UserDAO user = dbUser.findByUsername(username);

    if (user == null) {
      return false;
    }

    if (user.getSavingsAccount() == null) {
      transactionSocket.createTransaction(
          username,
          username,
          "Sparesti: Manual savings transfer towards savings goal",
          user.getCurrentAccount(),
          user.getSavingsAccount(),
          amount,
          "NOK");
    }

    return true;
  }
}
