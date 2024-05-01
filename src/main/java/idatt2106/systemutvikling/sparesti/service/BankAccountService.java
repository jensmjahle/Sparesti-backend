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

    /**
     * Method to get all accounts of a user. The method gets all accounts of a user based on
     * the consent of the PSU and returns a list of accounts.
     *
     * @return the account with the account number
     */
    public List<BankAccount> getAllAccountsForCurrentUser() {

        PSUConsent consent = new PSUConsent();
        consent.setPsuId(CurrentUserService.getCurrentUsername());

        return accountSocket.getAllAccountsOfUser(consent);
    }
}
