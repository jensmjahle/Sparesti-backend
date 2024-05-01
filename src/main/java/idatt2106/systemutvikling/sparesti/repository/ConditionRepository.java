package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ConditionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionDAO, Long> {

  /**
   * Method to find a condition by its id.
   *
   * @param id the id of the condition
   * @return the condition with the given id
   */
  ConditionDAO findConditionDAOByConditionId(Long id);

  /**
   * Method to find all conditions by achievement id.
   *
   * @param achievementId the id of the achievement
   * @return a list of conditions that belong to the achievement with the given id
   */
  List<ConditionDAO> findAllByAchievementDAO_AchievementId(Long achievementId);
}
