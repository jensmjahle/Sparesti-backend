package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

  @Mock
  private UserRepository UserRepository;

  @Mock
  private TransactionServiceInterface transactionSocket;

  @Mock
  private TransactionCategoryCacheService cacheService;

  @InjectMocks
  private TransactionService transactionService;

  public TransactionServiceTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test createSavingsTransferForUser_Success")
  void createSavingsTransferForUser_Success() {
    // Arrange
    String username = "testUser";
    Long checkingAccountId = 12345L;
    Long savingsAccountId = 54321L;
    Long amount = 100L;

    UserDAO user = new UserDAO();
    user.setCurrentAccount(checkingAccountId);
    user.setSavingsAccount(savingsAccountId);

    TransactionDAO transactionDAO = new TransactionDAO();

    transactionDAO.setTransactionTitle("Savings transfer");
    transactionDAO.setTime(new Date());
    transactionDAO.setDebtorAccount(checkingAccountId);
    transactionDAO.setDebtorName(username);
    transactionDAO.setCreditorAccount(savingsAccountId);
    transactionDAO.setCreditorName(username);
    transactionDAO.setAmount(amount);
    transactionDAO.setCurrency("NOK");

    when(UserRepository.findByUsername(username)).thenReturn(user);
    when(transactionSocket.createTransaction(username, username, "Savings transfer", checkingAccountId, savingsAccountId, amount, "NOK"))
            .thenReturn(true);

    // Act
    ResponseEntity<Boolean> response = transactionService.createSavingsTransferForUser(amount, username);

    // Assert
    assertEquals(200, response.getStatusCodeValue()); // Assuming successful response code is 200
    assertEquals(Boolean.TRUE, response.getBody());
  }

  @Test
  @DisplayName("Test createSavingsTransferForUser_Failure")
  void createSavingsTransferForUser_Failure() {
    // Arrange
    String username = "testUser";
    Long checkingAccountId = 12345L;
    Long savingsAccountId = 54321L;
    Long amount = 100L;

    UserDAO user = new UserDAO();
    user.setCurrentAccount(checkingAccountId);
    user.setSavingsAccount(savingsAccountId);

    TransactionDAO transactionDAO = new TransactionDAO();

    transactionDAO.setTransactionTitle("Savings transfer");
    transactionDAO.setTime(new Date());
    transactionDAO.setDebtorAccount(checkingAccountId);
    transactionDAO.setDebtorName(username);
    transactionDAO.setCreditorAccount(savingsAccountId);
    transactionDAO.setCreditorName(username);
    transactionDAO.setAmount(amount);
    transactionDAO.setCurrency("NOK");

    when(UserRepository.findByUsername(username)).thenReturn(null);

    // Act
    ResponseEntity<Boolean> response = transactionService.createSavingsTransferForUser(amount, username);

    // Assert
    assertEquals(200, response.getStatusCodeValue()); // Assuming bad request response code is 200
    assertNotEquals(Boolean.TRUE, response.getBody());
  }

  @Test
  @DisplayName("Test getLatestExpensesForUser_Success")
  void getLatestExpensesForUser_Success() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(12345L);

    Transaction transaction = new Transaction(
            1L,
            12345L,
            "Test transaction",
            new Date(),
            54321L,
            "JaneDoe",
            12345L,
            "JohnDoe",
            100L,
            "NOK"
    );

    when(UserRepository.findByUsername("JohnDoe")).thenReturn(user);
    when(transactionSocket.getLatestExpensesForAccountNumber(12345L, 1, 10)).thenReturn(List.of(transaction));

// Act
    List<Transaction> transactions = transactionService.getLatestExpensesForUser("JohnDoe", 1, 10);

    // Assert
    assertEquals(1, transactions.size());
    assertEquals(transaction, transactions.get(0));
  }

  @Test
  @DisplayName("Test getLatestExpensesForUser_Failure with null user")
  void getLatestExpensesForUser_Failure_NullUser() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(12345L);

    when(UserRepository.findByUsername("JohnDoe")).thenReturn(null);

    // Act
    List<Transaction> transactions = transactionService.getLatestExpensesForUser("JohnDoe", 1, 10);

    // Assert
    assertNull(transactions);
  }

  @Test
  @DisplayName("Test getLatestExpensesForUser_Failure with null account")
  void getLatestExpensesForUser_Failure_NullAccount() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(null);

    when(UserRepository.findByUsername("JohnDoe")).thenReturn(user);

    // Act
    List<Transaction> transactions = transactionService.getLatestExpensesForUser("JohnDoe", 1, 10);

    // Assert
    assertNull(transactions);
  }
}
