package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.PaginatedRequestDTO;
import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.exceptions.BankConnectionErrorException;
import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/transaction")
@AllArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  //todo: move logic to service layer
  @GetMapping("/latest/expense")
  public ResponseEntity<?> getLatestExpensesCategorized(Pageable pageable) {
    String username = CurrentUserService.getCurrentUsername();

    List<Transaction> transactions = transactionService
        .getLatestExpensesForUserCategorized(
            username,
            pageable.getPageNumber(),
            pageable.getPageSize()
        );

    if (transactions == null) {
      throw new BankConnectionErrorException();
    }

    // Convert transactions to their DTO counterpart.
    List<TransactionDTO> dtos = transactions
        .stream()
        .map(TransactionMapper::toDTO)
        .toList();

    return ResponseEntity.ok().body(dtos);
  }

  @GetMapping("/30-day-expenses")
  public ResponseEntity<List<TransactionDTO>> getLatestExpenses_LastMonth_Categorized() {
    List<Transaction> transactions = transactionService.getLatestExpensesFromCheckingAccountOfCurrentUserCategorized();
    if (transactions == null)
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    List<TransactionDTO> body = transactions
            .stream()
            .map(TransactionMapper::toDTO)
            .toList();


    return ResponseEntity.ok().body(body);
  }
}
