package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling transactions.
 */
@RestController
@RequestMapping("/user/transaction")
@AllArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @Operation(
      summary = "Get latest transactions",
      description = "Get the last 30 days of transactions for the current user"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Transactions found",
          content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = TransactionDTO.class))
          }
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No transactions found",
          content = @Content
      )
  })
  @GetMapping("/30-day-expenses")
  public ResponseEntity<List<TransactionDTO>> getLatestExpenses_LastMonth_Categorized() {
    List<Transaction> transactions = transactionService.getLatestExpensesForCurrentUser_CheckingAccount_Categorized();
    if (transactions == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    List<TransactionDTO> body = transactions
        .stream()
        .map(TransactionMapper::toDTO)
        .toList();

    return ResponseEntity.ok().body(body);
  }
}
