package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperTest {

  @Test
  public void testToDTO() {
    // Arrange
    Transaction transaction = new Transaction(1l, 123456789l, "Test Transaction", new Date(), 987654321l, "Test Debtor", 123456789l, "Test Creditor", 100l, "USD");

    // Act
    TransactionDTO dto = TransactionMapper.toDTO(transaction);

    // Assert
    assertEquals(transaction.getTransactionId(), dto.getTransactionId());
    assertEquals(transaction.getAmount(), dto.getAmount());
    assertEquals(transaction.getTime(), dto.getTime());
    assertEquals(transaction.getCurrency(), dto.getCurrency());
    assertEquals(transaction.getTransactionTitle(), dto.getTransactionTitle());
    assertEquals(transaction.getCreditorAccount(), dto.getCreditorAccount());
    assertEquals(transaction.getCreditorName(), dto.getCreditorName());
    assertEquals(transaction.getDebtorAccount(), dto.getDebtorAccount());
    assertEquals(transaction.getDebtorName(), dto.getDebtorName());
    assertEquals(transaction.getCategory(), dto.getTransactionCategory());
  }
}