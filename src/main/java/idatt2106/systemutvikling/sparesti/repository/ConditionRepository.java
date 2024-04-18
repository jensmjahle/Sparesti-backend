package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionDAO, Long> {

  ConditionDAO findConditionDAOByConditionId(Long id);

  List<ConditionDAO> findAllByAchievementDAO_AchievementId(Long achievementId);
}
