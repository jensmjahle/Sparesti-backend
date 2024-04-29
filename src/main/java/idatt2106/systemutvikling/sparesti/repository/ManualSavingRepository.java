package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ManualSavingRepository extends JpaRepository<ManualSavingDAO, Long> {

    List<ManualSavingDAO> findByUser_UsernameAndTimeOfTransferAfter(String username, Date threshold);

    void deleteAllByUser_Username(String username);
}
