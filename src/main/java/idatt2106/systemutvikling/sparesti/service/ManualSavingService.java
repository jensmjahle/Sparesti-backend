package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ManualSavingRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling manual saving entries.
 */
@Service
@AllArgsConstructor
public class ManualSavingService {

  private final UserRepository dbUser;
  private final ManualSavingRepository dbManualSaving;

  /**
   * Registers a new manual saving entry in the database. The entry is saved with the current time.
   *
   * @param milestoneId the milestone id to save the manual saving entry to
   * @param amount      the amount to save
   * @param username    the username of the user to save the manual saving entry for
   * @return the saved ManualSavingDAO
   */
  public ManualSavingDAO registerNewManualSavingDAO(Long milestoneId, Long amount,
      String username) {
    ManualSavingDAO dao = new ManualSavingDAO();

    if (username == null) {
      return null;
    }

    UserDAO user = dbUser.findByUsername(username);
    if (user == null) {
      return null;
    }

    dao.setUser(user);

    dao.setMilestoneId(milestoneId);
    dao.setTimeOfTransfer(new Date());
    dao.setAmount(amount);

    return dbManualSaving.save(dao);
  }

  /**
   * Retrieves all manual saving entries for a user. The entries are sorted by time of transfer.
   *
   * @param dao the user to retrieve manual saving entries for
   */
  public void removeManualSavingEntry(ManualSavingDAO dao) {
    dbManualSaving.delete(dao);
  }

  /**
   * Retrieves all manual saving entries for a user. The entries are sorted by time of transfer.
   *
   * @param manualSavingId the id of the manual saving entry to remove
   */
  public void removeManualSavingEntry(Long manualSavingId) {
    dbManualSaving.deleteById(manualSavingId);
  }

  /**
   * Retrieves all manual saving entries for a user. The entries are sorted by time of transfer.
   *
   * @param username the username of the user to retrieve manual saving entries for
   */
  public double getThisMonthTotalManualSavings(String username) {
    LocalDateTime startOfMth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0)
        .withSecond(0).withNano(0);
    Date threshold = java.sql.Timestamp.valueOf(startOfMth);
    List<ManualSavingDAO> savingDAOS = dbManualSaving.findByUser_UsernameAndTimeOfTransferAfter(
        username, threshold);
    if (savingDAOS == null) {
      return 0;
    }
    double total = 0;
    for (ManualSavingDAO dao : savingDAOS) {
      if (dao.getAmount() != null) {
        total += dao.getAmount();
      }
    }
    return total;
  }
}
