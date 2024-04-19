package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.PaginatedRequestDTO;
import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/latest/expense")
    public ResponseEntity<?> getLatestExpenses(final @RequestBody PaginatedRequestDTO pagination) {
        String username = CurrentUserService.getCurrentUsername();

        List<TransactionDTO> dtos = transactionService
                .getLatestExpensesForUser(
                        username,
                        pagination.getPageNum(),
                        pagination.getPageSize()
                );

        if (dtos == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No expenses found for the selected checkings account");

        return ResponseEntity.ok().body(dtos);
    }
}
