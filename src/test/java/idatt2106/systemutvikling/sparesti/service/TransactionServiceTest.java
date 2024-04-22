package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.service.AccountService;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

  @Mock
  private UserRepository UserRepository;

  @Mock
  private TransactionServiceInterface transactionSocket;

  @InjectMocks
  private TransactionService transactionService;

  public TransactionServiceTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
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
  }
}
