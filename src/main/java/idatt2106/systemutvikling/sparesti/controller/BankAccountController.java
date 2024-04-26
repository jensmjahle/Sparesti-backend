package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.BankAccountDTO;
import idatt2106.systemutvikling.sparesti.exceptions.NotFoundInDatabaseException;
import idatt2106.systemutvikling.sparesti.mapper.BankAccountMapper;
import idatt2106.systemutvikling.sparesti.service.BankAccountService;
import idatt2106.systemutvikling.sparesti.model.BankAccount;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/account")
@AllArgsConstructor
public class BankAccountController {

  private final Logger logger = Logger.getLogger(BankAccountController.class.getName());
  private BankAccountService srvAccount;


  @GetMapping
  //todo: move logic to service layer
  public ResponseEntity<?> getAllBankAccounts() {
    List<BankAccount> accounts = srvAccount.getAllAccountsForCurrentUser();

    if (accounts == null || accounts.isEmpty()) {
      throw new NotFoundInDatabaseException("No bank accounts found for current user.");
    }

    List<BankAccountDTO> body = accounts.stream()
        .map(BankAccountMapper::toDTO)
        .toList();

    return ResponseEntity.ok().body(body);
  }
}
