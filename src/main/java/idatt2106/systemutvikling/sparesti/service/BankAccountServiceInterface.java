package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.model.BankAccount;
import idatt2106.systemutvikling.sparesti.model.PSUConsent;

import java.util.List;

public interface BankAccountServiceInterface {

    List<BankAccount> getAllAccountsOfUser(PSUConsent consent);
}
