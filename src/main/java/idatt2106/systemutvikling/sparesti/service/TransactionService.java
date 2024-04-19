package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.mapper.KeywordMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
  Logger logger = Logger.getLogger(TransactionService.class.getName());
  OpenAIService openAIService;

  @Autowired
  public TransactionService(OpenAIService openAIService) {
    this.openAIService = openAIService;
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
