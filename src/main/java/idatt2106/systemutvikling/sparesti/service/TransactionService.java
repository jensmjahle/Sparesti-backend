package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.mapper.TransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.service.AccountService;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionServiceInterface transactionSocket;
    private final AccountService accountService;
    private final UserRepository dbUser;

    public List<TransactionDTO> getLatestExpensesForUser(String username, int page, int pageSize) {
        Long checkingAccount = dbUser
                .findByUsername(username)
                .getCurrentAccount();

        return transactionSocket
                .getLatestExpensesForAccountNumber(checkingAccount, page, pageSize)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();
    }
}
