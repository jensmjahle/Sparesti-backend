package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;

/**
 * Interface for the CustomerService class.
 */
public interface CustomerServiceInterface {

  /**
   * Method to check if a customer exists in the database. The method checks if the customer exists
   * based on the username and returns true if the customer exists, false otherwise.
   *
   * @param username the username of the customer
   * @return true if the customer exists, false otherwise
   */
  boolean customerExists(String username);

  /**
   * Method to check if a customer has two accounts. The method checks if the customer has two
   * accounts based on the username and returns true if the customer has two accounts, false
   * otherwise.
   *
   * @param username the username of the customer
   * @return true if the customer has two accounts, false otherwise
   */
  boolean hasTwoAccounts(String username);

  /**
   * Method to find a customer by the username. The method finds the customer based on the username
   * and returns the customer.
   *
   * @param username the username of the customer
   * @return the customer with the username
   */
  CustomerDAO findCustomerByUsername(String username);
}
