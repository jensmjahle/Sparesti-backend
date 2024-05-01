package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.exceptions.UserNotFoundException;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
  @DisplayName("Test getLatestExpensesForCurrentUser_CheckingAccount_Success")
  void getLatestExpensesForCurrentUser_CheckingAccount_Success() {
    // Arrange
    String username = "testUser";
    Long checkingAccountId = 12345L;
    Date date = new Date();

    UserDAO user = new UserDAO();
    user.setUsername(username);
    user.setCurrentAccount(checkingAccountId);

    Transaction transaction = new Transaction(
            1L,
            checkingAccountId,
            "Test transaction",
            new Date(),
            54321L,
            "JaneDoe",
            checkingAccountId,
            username,
            100L,
            "NOK"
    );

    TransactionCategoryDAO category = new TransactionCategoryDAO();
    category.setTransactionCategory(TransactionCategory.NOT_CATEGORIZED);

    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user.getUsername());
      when(UserRepository.findByUsername(user.getUsername())).thenReturn(user);

      when(transactionSocket.getLatestExpensesForAccountNumber(checkingAccountId, date)).thenReturn(List.of(transaction));
      when(cacheService.getCategoryFromCache(transaction.getTransactionId())).thenReturn(category);

      // Act
      List<Transaction> transactions = transactionService.getLatestExpensesForCurrentUser_CheckingAccount(date);

      // Assert
      assertEquals(1, transactions.size());
      assertEquals(transaction, transactions.get(0));
      assertEquals(TransactionCategory.NOT_CATEGORIZED, transactions.get(0).getCategory());
    }
  }

  @Test
  @DisplayName("Test getLatestExpensesForCurrentUser_CheckingAccount_Categorized_Failure throws UserNotFoundException")
  void getLatestExpensesForCurrentUser_CheckingAccount_Categorized_Failure_NullUser() {
    // Arrange
    Date date = new Date();

    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(null);

      // Assert
      assertThrows(UserNotFoundException.class, () -> transactionService.getLatestExpensesForCurrentUser_CheckingAccount(date));
    }
  }

  @Test
  @DisplayName("Test getLatestExpensesForUserCheckingAccount_Success")
  void getLatestExpensesForUserCheckingAccount_Success() {
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
    when(transactionSocket.getLatestExpensesForAccountNumber(user.getCurrentAccount(), new Date(0L))).thenReturn(List.of(transaction));

// Act
    List<Transaction> transactions = transactionService.getLatestExpensesForUser_CheckingAccount("JohnDoe", new Date(0L));

    // Assert
    assertEquals(1, transactions.size());
    assertEquals(transaction, transactions.get(0));
  }

  @Test
  @DisplayName("Test getLatestExpensesForUserCheckingAccount_Failure throws UserNotFoundException")
  void getLatestExpensesForUserCheckingAccount_Failure_NullUser() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(12345L);

    when(UserRepository.findByUsername("JohnDoe")).thenReturn(null);

    // Assert
    assertThrows(UserNotFoundException.class, () -> transactionService.getLatestExpensesForUser_CheckingAccount("JohnDoe", new Date(0L)));
  }

  @Test
  @DisplayName("Test getLatestExpensesForUser_Failure with null account")
  void getLatestExpensesForUserCheckingAccount_Failure_NullAccount() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(null);

    when(UserRepository.findByUsername("JohnDoe")).thenReturn(user);

    // Act
    List<Transaction> transactions = transactionService.getLatestExpensesForUser_CheckingAccount("JohnDoe", new Date(0L));

    // Assert
    assertNull(transactions);
  }


  @Test
  @DisplayName("Test categorizeTransactions_Success")
  void categorizeTransactions_Success() {
    // Arrange
    Transaction transaction1 = new Transaction(
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

    Transaction transaction2 = new Transaction(
            2L,
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

    List<Transaction> transactions = List.of(transaction1, transaction2);

    TransactionCategoryDAO category = new TransactionCategoryDAO();
    category.setTransactionCategory(TransactionCategory.NOT_CATEGORIZED);

    when(cacheService.getCategoryFromCache(transaction1.getTransactionId())).thenReturn(category);
    when(cacheService.getCategoryFromCache(transaction2.getTransactionId())).thenReturn(category);
    when(cacheService.setCategoryCache(transaction1.getTransactionId(), TransactionCategory.NOT_CATEGORIZED, new Date())).thenReturn(category);

    // Act
    List<Transaction> response = transactionService.categorizeTransactions(transactions);

    // Assert

    assertEquals(2, response.size());
    assertEquals(TransactionCategory.NOT_CATEGORIZED, response.get(0).getCategory());
    assertEquals(TransactionCategory.NOT_CATEGORIZED, response.get(1).getCategory());
  }

  @Test
  @DisplayName("Test createSavingsTransferForCurrentUser returns true")
  void createSavingsTransferForCurrentUser() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setSavingsAccount(12345L);
    user.setCurrentAccount(54321L);


    // Act
    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user.getUsername());
      when(UserRepository.findByUsername(user.getUsername())).thenReturn(user);
      when(transactionSocket.createTransaction(user.getUsername(), user.getUsername(), "Savings transfer", user.getCurrentAccount(), user.getSavingsAccount(), 100L, "NOK")).thenReturn(true);

      boolean response = transactionService.createSavingsTransferForCurrentUser(100L);

      // Assert
      assertTrue(response);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Test createSavingsTransferForCurrentUser returns false when username is null")
  void createSavingsTransferForCurrentUser_UsernameIsNull() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername(null);

    // Act
    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(null);

      boolean response = transactionService.createSavingsTransferForCurrentUser(100L);

      // Assert
      assertFalse(response);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }


  @Test
  @DisplayName("Test createSavingsTransferForCurrentUser returns false when user is null")
  void createSavingsTransferForCurrentUser_UserIsNull() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");

    // Act
    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user.getUsername());
      when(UserRepository.findByUsername(user.getUsername())).thenReturn(null);

      boolean response = transactionService.createSavingsTransferForCurrentUser(100L);

      // Assert
      assertFalse(response);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Test createSavingsTransferForCurrentUser returns false when savings account is null")
  void createSavingsTransferForCurrentUser_SavingsAccountIsNull() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(12345L);
    user.setSavingsAccount(null);

    // Act
    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user.getUsername());
      when(UserRepository.findByUsername(user.getUsername())).thenReturn(user);

      boolean response = transactionService.createSavingsTransferForCurrentUser(100L);

      // Assert
      assertFalse(response);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Test createSavingsTransferForCurrentUser returns false when current account is null")
  void createSavingsTransferForCurrentUser_CurrentAccountIsNull() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");
    user.setCurrentAccount(null);
    user.setSavingsAccount(12345L);

    // Act
    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user.getUsername());
      when(UserRepository.findByUsername(user.getUsername())).thenReturn(user);

      boolean response = transactionService.createSavingsTransferForCurrentUser(100L);

      // Assert
      assertFalse(response);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }
}
