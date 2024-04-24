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



    public ManualSavingDAO registerNewManualSavingDAO(Long milestoneId, Long amount) {
        ManualSavingDAO dao = new ManualSavingDAO();

        String username = CurrentUserService.getCurrentUsername();
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


    public void removeManualSavingEntry(ManualSavingDAO dao) {
        dbManualSaving.delete(dao);
    }

    public void removeManualSavingEntry(Long manualSavingId) {
        dbManualSaving.deleteById(manualSavingId);
    }
}
