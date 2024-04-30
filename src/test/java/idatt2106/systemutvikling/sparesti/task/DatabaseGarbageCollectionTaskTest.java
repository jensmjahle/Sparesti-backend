package idatt2106.systemutvikling.sparesti.task;

import idatt2106.systemutvikling.sparesti.service.TransactionCategoryCacheService;
import idatt2106.systemutvikling.sparesti.tasks.DatabaseGarbageCollectionTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class DatabaseGarbageCollectionTaskTest {

  @Mock
  private TransactionCategoryCacheService transactionCategoryCacheService;

  @InjectMocks
  private DatabaseGarbageCollectionTask databaseGarbageCollectionTask;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRunGarbageCollection() {
    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
    Date thirtyDaysAgoDate = java.sql.Date.valueOf(thirtyDaysAgo);

    databaseGarbageCollectionTask.runGarbageCollection();

    verify(transactionCategoryCacheService).deleteOutdatedCache(thirtyDaysAgoDate);
  }

}
