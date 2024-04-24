package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.model.BankAccount;
import idatt2106.systemutvikling.sparesti.model.PSUConsent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankAccountService {

    private BankAccountServiceInterface accountSocket;

    public List<BankAccount> getAllAccountsForCurrentUser() {
        // Manufactured consent. Will be done differently when we implement for actual APIs.
        PSUConsent consent = new PSUConsent();
        consent.setPsuId(CurrentUserService.getCurrentUsername());

        return accountSocket.getAllAccountsOfUser(consent);
    }
}
