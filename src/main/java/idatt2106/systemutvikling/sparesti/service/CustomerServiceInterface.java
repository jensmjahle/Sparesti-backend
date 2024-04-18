package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;

public interface CustomerServiceInterface {

    boolean customerExists(String username);

    boolean hasTwoAccounts(String username);

    CustomerDAO findCustomerByUsername(String username);
}
