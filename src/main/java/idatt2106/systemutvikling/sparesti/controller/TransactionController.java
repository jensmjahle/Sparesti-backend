package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/transaction")
@AllArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @GetMapping("/30-day-expenses")
  public ResponseEntity<List<TransactionDTO>> getLatestExpenses_LastMonth_Categorized() {
    List<Transaction> transactions = transactionService.getLatestExpensesForCurrentUser_CheckingAccount_Categorized();
    if (transactions == null)
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    List<TransactionDTO> body = transactions
            .stream()
            .map(TransactionMapper::toDTO)
            .toList();

    if (transactions.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok().body(body);
  }
}
