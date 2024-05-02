package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.model.BankAccount;
import idatt2106.systemutvikling.sparesti.model.PSUConsent;

import java.util.List;

/**
 * Interface for the BankAccountService class.
 */
public interface BankAccountServiceInterface {

    /**
     * Method to get all accounts of a user. The method gets all accounts of a user based on
     * the consent of the PSU and returns a list of accounts.
     *
     * @param consent the consent of the PSU
     * @return the account with the account number
     */
    List<BankAccount> getAllAccountsOfUser(PSUConsent consent);
}
