package idatt2106.systemutvikling.sparesti.service;


import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.mapper.KeywordMapper;
import idatt2106.systemutvikling.sparesti.mapper.TransactionCategoryMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
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

    private final TransactionServiceInterface transactionSocket;
    private final TransactionCategoryCacheService cacheService;
    private final UserRepository dbUser;
    private final OpenAIService openAIService;
    private final Logger logger = Logger.getLogger(TransactionService.class.getName());

    /**
     * Creates a savings transfer for a user.
     *
     * @param amount The amount to transfer.
     * @param username The username of the user.
     * @return ResponseEntity<TransactionDTO> The ResponseEntity containing the created transaction DTO.
     */
    public ResponseEntity<Boolean> createSavingsTransferForUser(Long amount, String username) {
        try {
            UserDAO user = dbUser.findByUsername(username);

            if (user == null)
                return ResponseEntity.ok(false);

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
            logger.severe("An error occurred while making savings transfer username " + username + ": " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    public List<Transaction> getLatestExpensesForUser(String username, int page, int pageSize) {
        UserDAO user = dbUser.findByUsername(username);

        if (user == null)
            return null;

        Long checkingAccount = user.getCurrentAccount();

        if (checkingAccount == null)
            return null;

        return transactionSocket.getLatestExpensesForAccountNumber(checkingAccount, page, pageSize);
    }

    /**
     * Retrieves the latest outgoing transactions for a given user, and makes sure to categorize them before
     * returning them.
     * @param username The username of the user, serving as the userId.
     * @param page
     * @param pageSize
     * @return A list of the latest outgoing transactions.
     */
    public List<Transaction> getLatestExpensesForUserCategorized(String username, int page, int pageSize) {
        List<Transaction> transactions = getLatestExpensesForUser(username, page, pageSize);

        if (transactions == null)
            return null;

        return categorizeTransactions(transactions);
    }

    /**
     * Sets the category field of all transactions in the given list of transactions. This function writes directly
     * on the objects and does copy the objects. The returned object is just a reference to the parameter object.
     * The categorization first checks the categorization cache, and uses the registered category if it finds an
     * entry for the transaction. If none is found, the transaction runs through the full categorization process,
     * and the result is stored in the cache.
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
                categoryDAO = cacheService.setCategoryCache(t.getTransactionId(), newCategory);
            }

            // Set category of the transaction
            t.setCategory(categoryDAO.getTransactionCategory());
        }

        return transactions;
    }

    public TransactionCategory categorizeTransaction(Transaction transaction) {
        try {
            logger.info("Categorizing transaction with title: " + transaction.getTransactionTitle() + ".");

            // CATEGORIZE TRANSACTION USING KEYWORDS
            TransactionCategory categoryKeyword = KeywordMapper.getCategory(transaction.getTransactionTitle());
            if (categoryKeyword != TransactionCategory.NOT_CATEGORIZED) {
                logger.info("Transaction with title: " + transaction.getTransactionTitle() + " was categorized as: " + categoryKeyword + " using keywords.");
                return categoryKeyword;
            }

            if (DISABLE_OPENAI_PROMPTS)
                return TransactionCategory.OTHER;

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
                    logger.info("Transaction with title: " + transaction.getTransactionTitle() + " was categorized as: " + categoryOpenAI + " using OpenAI.");
                    return categoryOpenAI;
                }
            } catch (IllegalArgumentException e) {
                logger.severe("Error categorizing transaction with title: " + transaction.getTransactionTitle() + " using OpenAI.");
            }

            // TRANSACTION NOT CATEGORIZED, CATEGORIZE AS OTHER
            logger.info("Transaction with title: " + transaction.getTransactionTitle() + " was not categorized. Categorizing as OTHER.");
            return TransactionCategory.OTHER;

        } catch (Exception e) {
            logger.severe("Failed to categorize transaction with title: " + transaction.getTransactionTitle() + ".");
            throw e;
        }
    }

    public boolean createSavingsTransferForCurrentUser(long amount) {
        String username = CurrentUserService.getCurrentUsername();
        if (username == null)
            return false;

        UserDAO user = dbUser.findByUsername(username);

        if (user == null)
            return false;

        if (user.getSavingsAccount() == null)


        transactionSocket.createTransaction(
                username,
                username,
                "Sparesti: Manual savings transfer towards savings goal",
                user.getCurrentAccount(),
                user.getSavingsAccount(),
                amount,
                "NOK");

        return true;
    }
}
