package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.mapper.KeywordMapper;
import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.service.AccountService;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionServiceInterface transactionSocket;
    private final UserRepository dbUser;
    private final OpenAIService openAIService;
    private final Logger logger = Logger.getLogger(TransactionService.class.getName());

    public List<TransactionDTO> getLatestExpensesForUser(String username, int page, int pageSize) {
        Long checkingAccount = dbUser
                .findByUsername(username)
                .getCurrentAccount();

        return transactionSocket
                .getLatestExpensesForAccountNumber(checkingAccount, page, pageSize)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();
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
}
