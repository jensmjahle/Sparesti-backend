package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ManualSavingRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ManualSavingService {

  private final UserRepository dbUser;
  private final ManualSavingRepository dbManualSaving;

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


  public void removeManualSavingEntry(ManualSavingDAO dao) {
    dbManualSaving.delete(dao);
  }

  public void removeManualSavingEntry(Long manualSavingId) {
    dbManualSaving.deleteById(manualSavingId);
  }

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
