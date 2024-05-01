package idatt2106.systemutvikling.sparesti.mockBank.mapper;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.model.BankAccount;

public class MockBankAccountMapper {

    /**
     * Method to map a BankAccount to an AccountDAO.
     *
     * @param dao the BankAccount to map
     * @return the AccountDAO
     */
    public static BankAccount toModel(AccountDAO dao) {
        return new BankAccount(
                dao.getAccountNr(),
                dao.getCustomerDAO().getUsername(),
                dao.getBalance(),
                dao.getName(),
                dao.getType(),
                dao.getCurrency()
        );
    }
}
