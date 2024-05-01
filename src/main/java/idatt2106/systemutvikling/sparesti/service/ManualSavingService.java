package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ManualSavingRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ManualSavingService {

    private final UserRepository dbUser;
    private final ManualSavingRepository dbManualSaving;

    /**
     * Registers a new manual saving entry in the database. The entry is saved with the current time.
     *
     * @param milestoneId the milestone id to save the manual saving entry to
     * @param amount the amount to save
     * @param username the username of the user to save the manual saving entry for
     * @return the saved ManualSavingDAO
     */
    public ManualSavingDAO registerNewManualSavingDAO(Long milestoneId, Long amount, String username) {
        ManualSavingDAO dao = new ManualSavingDAO();

        if (username == null)
            return null;

        UserDAO user = dbUser.findByUsername(username);
        if (user == null)
            return null;

        dao.setUser(user);

        dao.setMilestoneId(milestoneId);
        dao.setTimeOfTransfer(new Date());
        dao.setAmount(amount);

        return dbManualSaving.save(dao);
    }

    /**
     * Removes a manual saving entry from the database.
     *
     * @param dao the manual saving entry to remove
     */
    public void removeManualSavingEntry(ManualSavingDAO dao) {
        dbManualSaving.delete(dao);
    }

    /**
     * Removes a manual saving entry from the database.
     *
     * @param manualSavingId the id of the manual saving entry to remove
     */
    public void removeManualSavingEntry(Long manualSavingId) {
        dbManualSaving.deleteById(manualSavingId);
    }
}
