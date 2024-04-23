package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.BankAccountDTO;
import idatt2106.systemutvikling.sparesti.mapper.BankAccountMapper;
import idatt2106.systemutvikling.sparesti.service.BankAccountService;
import idatt2106.systemutvikling.sparesti.model.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/account")
public class BankAccountController {

    private BankAccountService srvAccount;

    @GetMapping
    public ResponseEntity<?> getAllBankAccounts() {
        List<BankAccount> accounts = srvAccount.getAllAccountsForCurrentUser();

        if (accounts == null || accounts.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        List<BankAccountDTO> body = accounts.stream()
                .map(BankAccountMapper::toDTO)
                .toList();

        return ResponseEntity.ok().body(body);
    }
}
