package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dto.BankAccountDTO;
import idatt2106.systemutvikling.sparesti.model.BankAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountMapperTest {

  @Test
  public void testToDTO() {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setAccountNr(Long.valueOf("123456789"));
    bankAccount.setUsername("testUsername");
    bankAccount.setBalance((long) 1000.0);
    bankAccount.setName("Test Account");
    bankAccount.setType("Savings");
    bankAccount.setCurrency("USD");

    BankAccountDTO dto = BankAccountMapper.toDTO(bankAccount);

    assertEquals(bankAccount.getUsername(), dto.getUsername());
    assertEquals(bankAccount.getBalance(), dto.getBalance());
    assertEquals(bankAccount.getName(), dto.getName());
    assertEquals(bankAccount.getType(), dto.getType());
    assertEquals(bankAccount.getCurrency(), dto.getCurrency());
  }
}