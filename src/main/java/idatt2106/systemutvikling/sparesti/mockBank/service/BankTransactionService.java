package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankTransactionService implements TransactionServiceInterface {

  private final TransactionRepository transactionRepository;

  @Override
  public List<Transaction> getLatestExpensesForAccountNumber(@NonNull Long accountNumber, @Positive int page, @Positive int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("time").ascending());

    return transactionRepository
            .findTransactionDAOByDebtorAccount(accountNumber, pageable)
            .stream()
            .map(BankTransactionService::toModel)
            .toList();
  }

  public static Transaction toModel(TransactionDAO dao) {
    return new Transaction(
            dao.getTransactionId(),
            dao.getAccountDAO().getAccountNr(),
            dao.getTransactionTitle(),
            dao.getTime(),
            dao.getDebtorAccount(),
            dao.getDebtorName(),
            dao.getCreditorAccount(),
            dao.getCreditorName(),
            dao.getAmount(),
            dao.getCurrency()
    );
  }
}
