package idatt2106.systemutvikling.sparesti.tasks;

import idatt2106.systemutvikling.sparesti.service.TransactionCategoryCacheService;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DatabaseGarbageCollectionTask {

  private final Logger logger = Logger.getLogger(DatabaseGarbageCollectionTask.class.getName());
  private final TransactionCategoryCacheService transactionCategoryCacheService;

  @Scheduled(cron = "${garbage.collection.cron.expression:0 0 0 * * *}")
  public void runGarbageCollection() {
    try {
      logger.info("Running garbage collection on the database.");
      LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
      Date thirtyDaysAgoDate = java.sql.Date.valueOf(thirtyDaysAgo);
      transactionCategoryCacheService.deleteOutdatedCache(thirtyDaysAgoDate);
    } catch (Exception e) {
      logger.severe(
          "An error occurred while running garbage collection on the database: " + e.getMessage());
    }

  }


}
