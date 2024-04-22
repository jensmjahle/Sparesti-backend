package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.mapper.MockBankTransactionMapper;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankTransactionService implements TransactionServiceInterface {

  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  private final EntityManager entityManager;

  @Override
  public List<Transaction> getLatestExpensesForAccountNumber(@NonNull Long accountNumber, @Positive int page, @Positive int pageSize) {
      List<AccountDAO> allAccountsForUser = accountService.findOtherAccountsOwnedBySameUser(accountNumber);

      if (allAccountsForUser == null)
        return null;



    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<TransactionDAO> criteriaQuery = criteriaBuilder.createQuery(TransactionDAO.class);
    Root<TransactionDAO> root = criteriaQuery.from(TransactionDAO.class);

    // A list of predicates each enforcing that the transactions creditor account is not equal the accounts owned by the user.
    List<Predicate> creditorNotEqualOwnAccountList = allAccountsForUser
            .stream()
            .map(AccountDAO::getAccountNr)
            .map((Long a) -> { return criteriaBuilder.notEqual(root.get("creditorAccount"), a); })
            .toList();

    // The predicate filtering out all accounts where the creditor is not equal
    Predicate creditorAccountNotOwn = creditorNotEqualOwnAccountList.get(0);
    for (Predicate p : creditorNotEqualOwnAccountList)
      creditorAccountNotOwn = criteriaBuilder.and(creditorAccountNotOwn, p);

    // Predicate only allowing transactions where the debtor is the parameter account.
    Predicate debtorAccountEqualAccountNumber = criteriaBuilder.equal(root.get("debtorAccount"), accountNumber);

    // The final predicate to use, only allowing transactions that are outgoing and not internal.
    Predicate transactionIsExpense = criteriaBuilder.
            and(debtorAccountEqualAccountNumber, creditorAccountNotOwn);



    return entityManager
            .createQuery(criteriaQuery.select(root)
                    .where(transactionIsExpense)
                    .orderBy(criteriaBuilder.desc(root.get("time"))))
            .setFirstResult(page * pageSize)
            .setMaxResults(pageSize)
            .getResultList()
            .stream()
            .map(MockBankTransactionMapper::toModel)
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
